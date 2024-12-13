package com.kzics.dbzuhc.roles.impl;

import com.kzics.dbzuhc.player.PlayerData;
import com.kzics.dbzuhc.roles.AdvancedRole;
import com.kzics.dbzuhc.teams.CampType;
import com.kzics.dbzuhc.transformation.Transformation;
import com.kzics.dbzuhc.transformation.impl.MajinVegetaTransformation;
import com.kzics.dbzuhc.transformation.impl.SuperVegetaTransformation;
import com.kzics.dbzuhc.transformation.impl.VegetaBlueTransformation;
import com.kzics.dbzuhc.util.TransformationItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VegetaRole extends AdvancedRole {

    public VegetaRole() {
        super("Vegeta");
        transformations.add(new SuperVegetaTransformation());
        transformations.add(new MajinVegetaTransformation());
        transformations.add(new VegetaBlueTransformation());
    }

    @Override
    public void onGameStart(Player player) {
        player.setMaxHealth(24.0);

        ItemStack transformationItem = TransformationItemUtil.createTransformationItem(player);
        player.getInventory().addItem(transformationItem);

        ItemStack activableItem = createActivableItem();
        player.getInventory().addItem(activableItem);
    }

    @Override
    public void onTransformation(PlayerData playerData, Transformation transformation) {
        int index = getTransformations().indexOf(transformation);
        int nextIndex = (index + 1) % getTransformations().size();
        Transformation nextTransformation = getTransformations().get(nextIndex);

        playerData.setCurrentTransformation(nextTransformation);
        Player player = Bukkit.getPlayer(playerData.getUuid());
        if (player != null) {
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), TransformationItemUtil.setCurrentTransformationIndex(player.getItemInHand(), nextIndex));
            nextTransformation.onActivate(player);
        }
    }

    @Override
    public void onAttack(PlayerData attack, Transformation transformation) {
        if (transformation.getAttack().canExecute(attack.getUuid())) {
            transformation.getAttack().execute(Bukkit.getPlayer(attack.getUuid()));
        }
    }



    private ItemStack createActivableItem() {
        ItemStack activable = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = activable.getItemMeta();
        meta.setDisplayName("§6Activable");
        meta.setLore(Arrays.asList(
                "§7Utilisez cet item pour déclencher l'attaque",
                "§7de votre transformation actuelle."
        ));
        activable.setItemMeta(meta);
        return activable;
    }

    @Override
    public CampType getCampType() {
        return CampType.UNIVERSE7;
    }
}