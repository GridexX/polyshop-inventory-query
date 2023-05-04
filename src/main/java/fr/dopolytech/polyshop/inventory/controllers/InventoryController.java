package fr.dopolytech.polyshop.inventory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.dopolytech.polyshop.inventory.dto.InventoryDto;
import fr.dopolytech.polyshop.inventory.dto.PutInventoryDto;
import fr.dopolytech.polyshop.inventory.models.Inventory;
import fr.dopolytech.polyshop.inventory.repositories.InventoryRepository;
import fr.dopolytech.polyshop.inventory.services.InventoryService;
import jakarta.ws.rs.core.Response.ResponseBuilder;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @GetMapping(produces = "application/json")
  public List<InventoryDto> getInventory() {
    return inventoryService.findAll();
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<InventoryDto> getInventoryById(@PathVariable("id") String id) {
    InventoryDto inv = inventoryService.findInventoryByProductId(id);
    if(inv == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(inv);
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(code = HttpStatus.CREATED)
  public InventoryDto addInventory(@RequestBody InventoryDto inventory) {
    return inventoryService.save(inventory);
  }

  @PutMapping(value = "/{id}", consumes = "application/json")
  public ResponseEntity<InventoryDto> updateInventory(@PathVariable("id") String id, @RequestBody PutInventoryDto putInventoryDto) {
    InventoryDto inv = inventoryService.update(id, putInventoryDto);
    return ResponseEntity.ok(inv);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteInventory(@PathVariable("id") String id) {
    inventoryService.deleteByProductId(id);
  }
}
