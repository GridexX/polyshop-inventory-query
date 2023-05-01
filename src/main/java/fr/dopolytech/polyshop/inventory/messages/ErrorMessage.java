package fr.dopolytech.polyshop.inventory.messages;

import java.util.List;
import fr.dopolytech.polyshop.inventory.messages.ProductItem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorMessage {
  String errorStatus;
  String message;
  String source;
  long orderId;
  List<ProductItem> products;

  public ErrorMessage(String errorStatus, String message, long orderId, List<ProductItem> products) {
    this.errorStatus = errorStatus;
    this.message = message;
    this.source = "missing_stock";
    this.products = products;
  }
}
