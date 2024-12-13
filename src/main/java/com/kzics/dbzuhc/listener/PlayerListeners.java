package com.kzics.dbzuhc.listener;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.player.PlayerData;
import com.kzics.dbzuhc.roles.AdvancedRole;
import com.kzics.dbzuhc.roles.impl.VegetaRole;
import com.kzics.dbzuhc.transformation.Transformation;
import com.kzics.dbzuhc.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    private final DbzUHC plugin;

    public PlayerListeners(DbzUHC plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = new PlayerData(player.getUniqueId());
        playerData.setRole(new VegetaRole());
        plugin.getPlayerDataManager().addPlayer(player.getUniqueId(), playerData);
        playerData.getRole().onGameStart(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
        if (playerData == null) return;

        ItemStack item = player.getItemInHand();
        if (item == null) return;

        if (NBTUtil.hasNBTTag(item, "transformation")) {
            AdvancedRole role = playerData.getRole();

            if (role == null) return;

            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Transformation current = playerData.getCurrentTransformation();
                int currentIndex = role.getTransformations().indexOf(current);
                int nextIndex = (currentIndex + 1) % role.getTransformations().size();

                Transformation nextTransformation = role.getTransformations().get(nextIndex);
                playerData.setCurrentTransformation(nextTransformation);

                player.setItemInHand(NBTUtil.setNBTTag(item, "current_transformation", String.valueOf(nextIndex)));
                player.sendMessage("§aTransformation sélectionnée : " + nextTransformation.getName());
            } else {
                Transformation current = playerData.getCurrentTransformation();
                if (current != null) {
                    current.onActivate(player);
                }
            }

            event.setCancelled(true); // Empêche l'action par défaut
        } else {
            Transformation current = playerData.getCurrentTransformation();
            if (current != null) {
                current.getAttack().execute(player);
            }
        }
    }
}
