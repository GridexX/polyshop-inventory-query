package fr.dopolytech.polyshop.inventory.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateInventoryCommand {
  String id;
  long quantity;
  LocalDateTime date;
}
