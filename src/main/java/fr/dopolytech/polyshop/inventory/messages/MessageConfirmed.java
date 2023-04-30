package fr.dopolytech.polyshop.inventory.messages;

public class MessageConfirmed {
  public long orderId;
  public String status = "confirmed";

  public MessageConfirmed() {
  }

  public MessageConfirmed(long orderId) {
    this.orderId = orderId;
  }
}