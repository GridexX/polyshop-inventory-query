package fr.dopolytech.polyshop.inventory.messages;

import java.util.List;

public class InventoryMessage {
  public long orderId;
  public List<ProductItem> products;

  public InventoryMessage() {
  }

  public InventoryMessage(long orderId, List<ProductItem> products) {
    this.orderId = orderId;
    this.products = products;
  }
}