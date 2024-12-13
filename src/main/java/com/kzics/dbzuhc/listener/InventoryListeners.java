package com.kzics.dbzuhc.listener;

import com.kzics.dbzuhc.menu.UHCMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListeners implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();

        if (inventory == null) {
            return;
        }

        if(inventory.getHolder() instanceof UHCMenu) {
            UHCMenu menu = (UHCMenu) inventory.getHolder();
            menu.handle(event);
        }
    }
}
