package fr.dopolytech.polyshop.inventory.dto;

import fr.dopolytech.polyshop.inventory.models.Inventory;

public class InventoryDto {
  public String id;
  public long quantity;

  public InventoryDto(String id, long quantity) {
    this.id = id;
    this.quantity = quantity;
  }

  public InventoryDto() {
  }

  public InventoryDto(Inventory inventory) {
    this.id = inventory.productId;
    this.quantity = inventory.quantity;
  }
}
