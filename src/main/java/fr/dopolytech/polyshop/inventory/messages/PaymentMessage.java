package fr.dopolytech.polyshop.inventory.messages;

public class PaymentMessage {
  public long orderId;

  public PaymentMessage() {
  }

  public PaymentMessage(long orderId) {
    this.orderId = orderId;
  }

  
}
