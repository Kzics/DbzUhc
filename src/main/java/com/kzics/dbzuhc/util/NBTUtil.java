package com.kzics.dbzuhc.util;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    /**
     * Vérifie si un item contient un tag NBT spécifique.
     *
     * @param item  L'ItemStack à vérifier.
     * @param tag   Le nom du tag à vérifier.
     * @return true si le tag existe, sinon false.
     */
    public static boolean hasNBTTag(ItemStack item, String tag) {
        if (item == null || item.getType() == org.bukkit.Material.AIR) {
            return false;
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem == null || nmsItem.getTag() == null) {
            return false;
        }

        System.out.println(nmsItem.getTag());

        return nmsItem.getTag().hasKey(tag);
    }

    /**
     * Définit un tag NBT sur un ItemStack.
     *
     * @param item  L'ItemStack à modifier.
     * @param tag   Le nom du tag à définir.
     * @param value La valeur du tag.
     * @return L'ItemStack modifié.
     */
    public static ItemStack setNBTTag(ItemStack item, String tag, String value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        System.out.println("NMS ITEM BEFORE");
        if (nmsItem == null) {
            return item;
        }

        System.out.println("NMS ITEM AFTER");


        NBTTagCompound tagCompound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        tagCompound.setString(tag, value);  // Ajout du tag avec la valeur
        nmsItem.setTag(tagCompound);  // Associe le tag à l'item

        return CraftItemStack.asBukkitCopy(nmsItem);  // Convertit l'ItemStack NMS en Bukkit
    }




    /**
     * Obtient la valeur d'un tag NBT sur un ItemStack.
     *
     * @param item L'ItemStack à lire.
     * @param tag  Le nom du tag à lire.
     * @return La valeur du tag, ou null si le tag n'existe pas.
     */
    public static String getNBTTag(ItemStack item, String tag) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem == null || nmsItem.getTag() == null) {
            return null;
        }

        return nmsItem.getTag().getString(tag);
    }
}

