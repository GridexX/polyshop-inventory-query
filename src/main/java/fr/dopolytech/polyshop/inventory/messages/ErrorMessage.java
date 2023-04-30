package fr.dopolytech.polyshop.inventory.messages;

import java.util.List;
import fr.dopolytech.polyshop.inventory.messages.ProductItem;

public class ErrorMessage {
  String errorStatus;
  String message;
  String source;
  long orderId;
  List<ProductItem> products;

  public ErrorMessage(String errorStatus, String message, long orderId, List<ProductItem> products) {
    this.errorStatus = errorStatus;
    this.message = message;
    this.source = "inventory";
    this.products = products;
  }
}
