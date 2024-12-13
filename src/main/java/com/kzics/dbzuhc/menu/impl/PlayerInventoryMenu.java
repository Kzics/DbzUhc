package com.kzics.dbzuhc.menu.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.menu.UHCMenu;
import com.kzics.dbzuhc.game.UHCGame;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventoryMenu extends UHCMenu {

    public PlayerInventoryMenu(UHCGame uhcGame) {
        super(uhcGame);
    }

    @Override
    public void open(Player player) {
        player.closeInventory();
        this.addBorder();
        ItemStack arrowItem = createItem(Material.ARROW, "§aSauvegarder", "", "§7❱ Clique pour sauvegarder");
        inventory.setItem(49, arrowItem);

        player.openInventory(inventory);
    }

    @Override
    public void handle(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();

        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.ARROW) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            ItemStack[] startingItems = new ItemStack[36];
            for (int i = 0; i < 36; i++) {
                startingItems[i] = inventory.getItem(i);
            }
            game.setStartingItems(startingItems);
            player.sendMessage("§aStarting items have been saved!");
            event.setCancelled(true);
            new MainConfigMenu(game, DbzUHC.getInstance().getRoleManager()).open((Player) event.getWhoClicked());

        }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}