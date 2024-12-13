package com.kzics.dbzuhc.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class TransformationItemUtil {

    private static final String TRANSFORMATION_KEY = "current_transformation";

    public static ItemStack createTransformationItem(Player player) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Transformation");
        meta.setLore(Collections.singletonList("§7Clique droit pour changer de transformation"));
        item.setItemMeta(meta);
        item = NBTUtil.setNBTTag(item, "transformation", "true");

        return NBTUtil.setNBTTag(item, TRANSFORMATION_KEY, "0");
    }

    public static int getCurrentTransformationIndex(ItemStack item) {
        String index = NBTUtil.getNBTTag(item, TRANSFORMATION_KEY);
        return (index != null) ? Integer.parseInt(index) : 0;
    }

    public static ItemStack setCurrentTransformationIndex(ItemStack item, int index) {
        return NBTUtil.setNBTTag(item, TRANSFORMATION_KEY, String.valueOf(index));
    }
}
