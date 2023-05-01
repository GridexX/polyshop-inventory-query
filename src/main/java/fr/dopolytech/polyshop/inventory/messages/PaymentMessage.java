package fr.dopolytech.polyshop.inventory.messages;

import java.util.List;

public class PaymentMessage {
  public long orderId;
  public List<ProductItem> products;

  public PaymentMessage() {
  }

  public PaymentMessage(long orderId, List<ProductItem> products) {
    this.orderId = orderId;
    this.products = products;
  }

  
}
