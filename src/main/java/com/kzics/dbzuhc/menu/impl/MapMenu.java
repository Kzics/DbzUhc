package com.kzics.dbzuhc.menu.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.game.UHCGame;
import com.kzics.dbzuhc.menu.UHCMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MapMenu extends UHCMenu {
    public MapMenu(UHCGame game) {
        super(game);
    }

    @Override
    public void open(Player player) {
        this.addBorder();
        ItemStack arrowItem = createItem(Material.ARROW, "§aSauvegarder", "", "§7❱ Clique pour sauvegarder");
        ItemStack borderItem = createItem(Material.BARRIER, "§cBorder", "", "&7❱ " + formatSize(game.getGameConfig().getMapSize()));

        inventory.setItem(49, arrowItem);
        inventory.setItem(31, borderItem);
        player.openInventory(inventory);
    }

    @Override
    public void handle(InventoryClickEvent event) {
        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        if(clickedItem.getType() == Material.ARROW) {
            new MainConfigMenu(game, DbzUHC.getInstance().getRoleManager()).open((Player) event.getWhoClicked());
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (clickedItem.getType() == Material.BARRIER) {
            updateSize(event.getClick(), game.getGameConfig()::getMapSize, game.getGameConfig()::setMapSize);
            open(player);
        }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    private void updateSize(ClickType clickType, java.util.function.Supplier<Integer> getter, java.util.function.Consumer<Integer> setter) {
        int size = getter.get();
        if (clickType == ClickType.LEFT) {
            setter.accept(incrementSize(size));
        } else if (clickType == ClickType.RIGHT) {
            setter.accept(decrementSize(size));
        }
    }

    private int decrementSize(int size) {
        return Math.max(0, size - 100);
    }

    private int incrementSize(int size) {
        return size + 100;
    }

    private String formatSize(int size) {
        return size + " blocs";
    }
}