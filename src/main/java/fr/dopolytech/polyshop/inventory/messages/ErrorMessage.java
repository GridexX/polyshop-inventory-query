package fr.dopolytech.polyshop.inventory.messages;

public class ErrorMessage {
  String errorStatus;
  String message;
  String source;
  String orderId;

  public ErrorMessage(String errorStatus, String message, String orderId) {
    this.errorStatus = errorStatus;
    this.message = message;
    this.source = "inventory";
  }
}
