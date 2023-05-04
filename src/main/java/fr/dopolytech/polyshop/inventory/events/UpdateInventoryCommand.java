package fr.dopolytech.polyshop.inventory.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateInventoryCommand {
  String id;
  long quantity;
  LocalDateTime date;
}

