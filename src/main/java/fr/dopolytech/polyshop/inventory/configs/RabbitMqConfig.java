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
}