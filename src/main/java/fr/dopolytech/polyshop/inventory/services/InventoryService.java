package fr.dopolytech.polyshop.inventory.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dopolytech.polyshop.inventory.dto.InventoryDto;
import fr.dopolytech.polyshop.inventory.dto.PutInventoryDto;
import fr.dopolytech.polyshop.inventory.messages.ErrorEnum;
import fr.dopolytech.polyshop.inventory.messages.ErrorMessage;
import fr.dopolytech.polyshop.inventory.messages.InventoryMessage;
import fr.dopolytech.polyshop.inventory.messages.MessageConfirmed;
import fr.dopolytech.polyshop.inventory.messages.PaymentMessage;
import fr.dopolytech.polyshop.inventory.messages.ProductItem;
import fr.dopolytech.polyshop.inventory.models.Inventory;
import fr.dopolytech.polyshop.inventory.repositories.InventoryRepository;

import org.springframework.amqp.core.Queue;

@Component
public class InventoryService {

  private final RabbitTemplate rabbitTemplate;
  private final Queue inventoryCancelQueue;
  private final Queue inventoryConfirmedQueue;
  private final Queue paymentQueue;
  private final InventoryRepository inventoryRepository;

  // Define the logger
  private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

  @Autowired
  public InventoryService(RabbitTemplate rabbitTemplate, Queue inventoryCancelQueue, Queue inventoryConfirmedQueue, Queue paymentQueue,
      InventoryRepository inventoryRepository) {
    this.rabbitTemplate = rabbitTemplate;
    this.inventoryCancelQueue = inventoryCancelQueue;
    this.inventoryConfirmedQueue = inventoryConfirmedQueue;
    this.paymentQueue = paymentQueue;
    this.inventoryRepository = inventoryRepository;
  }

  public List<InventoryDto> findAll() {
    return inventoryRepository.findAll().stream().map(InventoryDto::new).collect(java.util.stream.Collectors.toList());
    // convert to dto

  }

  public InventoryDto findInventoryByProductId(String productId) {
    Inventory inv = inventoryRepository.findByProductId(productId);
    if(inv == null) {
      return null;
    }
    return new InventoryDto(inv);
  }

  public InventoryDto save(InventoryDto inventory) {
    inventoryRepository.save(new Inventory(inventory));
    return inventory;
  }

  public InventoryDto update(String id, PutInventoryDto inventory) {
    Inventory inv = inventoryRepository.findByProductId(id);
    if(inv == null) {
      return null;
    }
    inv.quantity = inventory.quantity;
    inventoryRepository.save(inv);
    return new InventoryDto(inv);
  }

  public void deleteByProductId(String productId) {
    inventoryRepository.deleteByProductId(productId);
  }

  public void receiveMessage(String messageBody) {
    // String messageBody = new String(message);
    logger.info("Received message from order queue: " + messageBody);
    try {
      ObjectMapper mapper = new ObjectMapper();
      InventoryMessage inventoryMessage = mapper.readValue(messageBody, InventoryMessage.class);

      // Check if each product exist in the inventory message and if the quantity
      // exists
      // If one of this conditions isn't true, return an error.

      for (int i = 0; i < inventoryMessage.products.size(); i++) {

        ProductItem product = inventoryMessage.products.get(i);

        Inventory optionalInventory = inventoryRepository.findByProductId(product.productId);

        ErrorMessage errorMessage = null;

        if (optionalInventory != null) {
          Inventory inventory = optionalInventory;
          logger.info("Inventory entry found : " + inventory.toString());

          long quantity = inventory.quantity;

          // Check if the quantity is sufficient
          if (quantity >= product.amount) {

            // Update the quantity of the inventory
            inventory.quantity = quantity - product.amount;
            inventoryRepository.save(inventory);

            // Send message to payment queue

            PaymentMessage paymentMessage = new PaymentMessage(inventoryMessage.orderId, inventoryMessage.products);
            String paymentMessageString = mapper.writeValueAsString(paymentMessage);
            logger.info("Send message to payment queue : " + paymentMessageString);
            rabbitTemplate.convertAndSend(paymentQueue.getName(), paymentMessageString);

          } else {

            // Quantity insufficient
            errorMessage = new ErrorMessage(ErrorEnum.PRODUCT_QUANTITY_MISSING,
                "Quantity is not sufficient : " + quantity, inventoryMessage.orderId, inventoryMessage.products);

            logger.error("Quantity is not sufficient : " + quantity);
          }

        } else {

          // Product not found
          errorMessage = new ErrorMessage(ErrorEnum.PRODUCT_NOT_FOUND, "Product id not found : " + product.productId,
              inventoryMessage.orderId, inventoryMessage.products);
          logger.error("Product id not found : " + product.productId);
        }

        if (errorMessage != null) {
          String errorMessageString = mapper.writeValueAsString(inventoryMessage);
          logger.error("Send message to inventory cancel queue : " + errorMessageString);
          rabbitTemplate.convertAndSend(inventoryCancelQueue.getName(), errorMessageString);
        } else {
          MessageConfirmed inventoryConfirmedMessage = new MessageConfirmed(inventoryMessage.orderId);
          String messageConfirmedString = mapper.writeValueAsString(inventoryConfirmedMessage);
          logger.info("Send a message to the inventory confirmed queue" );
          rabbitTemplate.convertAndSend(inventoryConfirmedQueue.getName(), messageConfirmedString);

          // Send message to payment queue
          PaymentMessage paymentMessage = new PaymentMessage(inventoryMessage.orderId, inventoryMessage.products);
          String paymentMessageString = mapper.writeValueAsString(paymentMessage);
          logger.info("Send message to payment queue : " + paymentMessageString);
          rabbitTemplate.convertAndSend(paymentQueue.getName(), paymentMessageString);
        }

      }

    } catch (Exception e) {
      logger.error(messageBody, e);
    }
  }

  // Receive message from payment service
  public void receiveMessageCancel(String message) {
    logger.info("Received message from payment cancel queue: " + message);

    // It should readd the quantity to the inventory
    ObjectMapper mapper = new ObjectMapper();
    ErrorMessage errorMessage = mapper.convertValue(message, ErrorMessage.class);

    errorMessage.products.forEach(product -> {
      Inventory inventory = inventoryRepository.findByProductId(product.productId);
      if (inventory != null) {
        inventory.quantity += product.amount;
        logger.info("Inventory entry found Added quantity: " + inventory.toString());
        inventoryRepository.save(inventory);
      } else {
        // This should never happen because the inventory should be checked before
        logger.error("Product id not found : " + product.productId);
      }
    });
    
    // Just transmit the message to the inventory cancel queue
    rabbitTemplate.convertAndSend(inventoryCancelQueue.getName(), message);
  }

}
