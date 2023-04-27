package fr.dopolytech.polyshop.inventory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.dopolytech.polyshop.inventory.models.Inventory;
import fr.dopolytech.polyshop.inventory.repositories.InventoryRepository;
import fr.dopolytech.polyshop.inventory.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @GetMapping(produces = "application/json")
  public List<Inventory> getInventory() {
    return inventoryService.findAll();
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public Inventory getInventoryById(@PathVariable("id") String id) {
    return inventoryService.findInventoryByProductId(id);
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(code = HttpStatus.CREATED)
  public Inventory addInventory(@RequestBody Inventory inventory) {
    return inventoryService.save(inventory);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteInventory(@PathVariable("id") String id) {
    inventoryService.deleteByProductId(id);
  }
}
