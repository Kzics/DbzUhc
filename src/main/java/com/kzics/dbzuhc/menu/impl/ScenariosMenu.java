package com.kzics.dbzuhc.menu.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.game.UHCGame;
import com.kzics.dbzuhc.menu.UHCMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ScenariosMenu extends UHCMenu {
    public ScenariosMenu(UHCGame game) {
        super(game);
    }

    @Override
    public void open(Player player) {
        this.addBorder();
        ItemStack arrowItem = createItem(Material.ARROW, "§aSauvegarder", "", "§7❱ Clique pour sauvegarder");
        inventory.setItem(49, arrowItem);
        player.openInventory(inventory);
    }

    @Override
    public void handle(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if(clickedItem.getType() == Material.ARROW) {
            new MainConfigMenu(game, DbzUHC.getInstance().getRoleManager()).open((Player) event.getWhoClicked());
            return;
        }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
