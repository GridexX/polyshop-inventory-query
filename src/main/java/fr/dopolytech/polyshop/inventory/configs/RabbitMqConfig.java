package fr.dopolytech.polyshop.inventory.configs;

import org.springframework.amqp.core.Queue;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.dopolytech.polyshop.inventory.services.InventoryService;


@Configuration
public class RabbitMqConfig {

  // Queue used to listen for command from the order service
  @Bean
  public Queue inventoryQueue() {
    return new Queue("inventory", false);
  }

  // Queue used to send commands cancel commands to the order service
  @Bean
  public Queue inventoryCancelQueue() {
    return new Queue("inventory_cancel", false);
  }

  // Queue used to send commands confirmed commands to the order service
  @Bean
  public Queue inventoryConfirmedQueue() {
    return new Queue("inventory_confirmed", false);
  }

  // Queue used to send commands to the payment service
  @Bean
  public Queue paymentQueue() {
    return new Queue("payment", false);
  }

  // Queue used to listen cancel commands from the payment service
  @Bean
  public Queue paymentCancelQueue() {
    return new Queue("payment_cancel", false);
  }

  // Queue used to listen for the inventory_command service
  @Bean
  public Queue inventoryCommandUpdateQueue() {
    return new Queue("inventory_command_update", false);
  }

  @Bean
  public Queue inventoryCommandCreateQueue() {
    return new Queue("inventory_command_create", false);
  }
  @Bean
  public Queue inventoryCommandDeleteQueue() {
    return new Queue("inventory_command_delete", false);
  }

  @Bean
  public MessageListenerAdapter listenerAdapter(InventoryService inventoryService) {
    return new MessageListenerAdapter(inventoryService, "receiveMessage");
  }

  @Bean
  public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames("inventory");
    container.setMessageListener(listenerAdapter);
    return container;
  }

  @Bean
  public MessageListenerAdapter listenerAdapterCancel(InventoryService inventoryService) {
    return new MessageListenerAdapter(inventoryService, "receiveMessageCancel");
  }

  @Bean
  public SimpleMessageListenerContainer containerCancel(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapterCancel) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames("payment_cancel");
    container.setMessageListener(listenerAdapterCancel);
    return container;
  }

  @Bean
  public MessageListenerAdapter listenerAdapterCommandUpdate(InventoryService inventoryService) {
    return new MessageListenerAdapter(inventoryService, "receiveMessageCommandUpdate");
  }

  @Bean
  public SimpleMessageListenerContainer containerCommandUpdate(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapterCommandUpdate) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(inventoryCommandUpdateQueue().getName());
    container.setMessageListener(listenerAdapterCommandUpdate);
    return container;
  }
  @Bean
  public MessageListenerAdapter listenerAdapterCommandCreate(InventoryService inventoryService) {
    return new MessageListenerAdapter(inventoryService, "receiveMessageCommandCreate");
  }

  @Bean
  public SimpleMessageListenerContainer containerCommandCreate(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapterCommandCreate) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(inventoryCommandCreateQueue().getName());
    container.setMessageListener(listenerAdapterCommandCreate);
    return container;
  }
  @Bean
  public MessageListenerAdapter listenerAdapterCommandDelete(InventoryService inventoryService) {
    return new MessageListenerAdapter(inventoryService, "receiveMessageCommandDelete");
  }

  @Bean
  public SimpleMessageListenerContainer containerCommandDelete(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapterCommandDelete) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(inventoryCommandDeleteQueue().getName());
    container.setMessageListener(listenerAdapterCommandDelete);
    return container;
  }
}