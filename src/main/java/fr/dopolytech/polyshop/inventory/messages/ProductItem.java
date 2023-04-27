package fr.dopolytech.polyshop.inventory.messages;

public class ProductItem {
  public String productId;
  public long amount;

  public ProductItem() {
  }

  public ProductItem(String productId, long amount) {
    this.productId = productId;
    this.amount = amount;
  }
}