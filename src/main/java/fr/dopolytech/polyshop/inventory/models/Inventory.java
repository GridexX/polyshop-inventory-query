package fr.dopolytech.polyshop.inventory.models;

import fr.dopolytech.polyshop.inventory.dto.InventoryDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int id;

  public String productId;

  public long quantity;

  public Inventory() {
  }

  public Inventory(String productId, long quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public Inventory(InventoryDto inventoryDto) {
    this.productId = inventoryDto.id;
    this.quantity = inventoryDto.quantity;
  }
}
