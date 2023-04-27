package fr.dopolytech.polyshop.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dopolytech.polyshop.inventory.models.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
  public Inventory findByProductId(String productId);

  public void deleteByProductId(String productId);
}
