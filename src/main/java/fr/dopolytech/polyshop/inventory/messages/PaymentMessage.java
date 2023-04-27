package fr.dopolytech.polyshop.inventory.messages;

public class PaymentMessage {
  public String orderId;

  public PaymentMessage() {
  }

  public PaymentMessage(String orderId) {
    this.orderId = orderId;
  }

  
}
