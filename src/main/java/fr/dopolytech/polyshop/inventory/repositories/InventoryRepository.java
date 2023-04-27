package fr.dopolytech.polyshop.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.dopolytech.polyshop.inventory.models.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
  public Inventory findByProductId(String productId);

  public void deleteByProductId(String productId);
}
