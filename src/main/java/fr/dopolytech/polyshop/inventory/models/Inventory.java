package fr.dopolytech.polyshop.inventory.models;

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

  public double price;

  public long quantity;

  public Inventory() {
  }

  public Inventory(String productId, double price, long quantity) {
    this.productId = productId;
    this.price = price;
    this.quantity = quantity;
  }
}
