package com.kzics.dbzuhc.menu.impl;

import com.kzics.dbzuhc.game.UHCGame;
import com.kzics.dbzuhc.manager.RoleManager;
import com.kzics.dbzuhc.menu.UHCMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class MainConfigMenu extends UHCMenu {
    private final RoleManager roleManager;
    public MainConfigMenu(UHCGame game, RoleManager roleManager) {
        super(game);
        this.roleManager = roleManager;
    }

    @Override
    public void open(Player player) {
        this.addBorder();

        ItemStack timerItem = createItem(Material.WATCH, "&e● &fTimer", "","&7❱ Clique pour configurer");
        ItemStack inventoryItem = createItem(Material.CHEST, "&e● &fInventaire", "&7❱ Clique pour configurer");
        ItemStack compItem = createItem(Material.REDSTONE_TORCH_ON, "&e● &fComposition des Roles", "&7❱ Clique pour configurer");
        ItemStack scenarioItem = createItem(Material.BOOK_AND_QUILL, "&e● &fScenarios", "&7❱ Clique pour configurer");
        ItemStack eventItem = createItem(Material.TNT, "&e● &fEvent", "&7❱ Clique pour configurer");
        ItemStack mapItem = createItem(Material.PAPER, "&e● &fMap", "&7❱ Clique pour configurer");
        ItemStack launchItem = createItem(Material.EMERALD, "&aLancer la partie", "", "&7❱ Clique pour lancer la partie");

        inventory.setItem(11, timerItem);
        inventory.setItem(15, inventoryItem);
        inventory.setItem(30, compItem);
        inventory.setItem(32, scenarioItem);
        inventory.setItem(40, eventItem);
        inventory.setItem(4, mapItem);
        inventory.setItem(49, launchItem);

        player.openInventory(inventory);
    }


    @Override
    public void handle(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.getType() == Material.WATCH) {
            new TimerConfigMenu(game).open(player);
        } else if (item.getType() == Material.CHEST) {
            new PlayerInventoryMenu(game).open(player);
        } else if (item.getType() == Material.REDSTONE_TORCH_ON) {
            new RolesMenu(game,roleManager).open(player);
        } else if (item.getType() == Material.BOOK_AND_QUILL) {
            new ScenariosMenu(game).open(player);
        } else if(item.getType() == Material.TNT){
            new MapMenu(game).open(player);
        } else if(item.getType() == Material.PAPER){
            new MapMenu(game).open(player);
        }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}