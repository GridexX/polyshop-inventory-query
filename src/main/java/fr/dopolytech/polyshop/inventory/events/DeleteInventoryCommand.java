package fr.dopolytech.polyshop.inventory.events;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeleteInventoryCommand {
  String id;
  LocalDateTime date;
}
