package com.kzics.dbzuhc.menu;

import com.kzics.dbzuhc.game.UHCGame;
import com.kzics.dbzuhc.util.ColorsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class UHCMenu implements InventoryHolder {

    protected final Inventory inventory;
    protected final UHCGame game;
    public UHCMenu(UHCGame game) {
        this.game = game;
        this.inventory = Bukkit.createInventory(this, 54, "Configuration");
    }

    protected void addBorder() {
        inventory.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(9, new ItemStack(Material.STAINED_GLASS_PANE));

        inventory.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(17, new ItemStack(Material.STAINED_GLASS_PANE));

        inventory.setItem(36, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(45, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(46, new ItemStack(Material.STAINED_GLASS_PANE));

        inventory.setItem(44, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(52, new ItemStack(Material.STAINED_GLASS_PANE));
        inventory.setItem(53, new ItemStack(Material.STAINED_GLASS_PANE));
    }

    protected ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ColorsUtil.translate.apply(name));
            List<String> coloredLore = Arrays
                    .stream(lore)
                    .map(s-> ColorsUtil.translate.apply(s))
                    .collect(Collectors.toList());

            meta.setLore(coloredLore);
            item.setItemMeta(meta);
        }
        return item;
    }
    public abstract void open(Player player);
    public abstract void handle(InventoryClickEvent event);
}
