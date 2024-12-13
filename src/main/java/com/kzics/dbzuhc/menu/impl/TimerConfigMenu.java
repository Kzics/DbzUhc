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

import java.util.Objects;

public class TimerConfigMenu extends UHCMenu {
    public TimerConfigMenu(UHCGame game) {
        super(game);
    }

    @Override
    public void open(Player player) {
        this.addBorder();
        ItemStack timeCycle = createItem(Material.WATCH, "&e● &fCycle de Jour/Nuit", "", "&7❱ " + formatTime(game.getGameConfig().getCycleTime()));
        ItemStack timePvp = createItem(Material.WATCH, "&e● &fTemps avant le PvP", "", "&7❱ " + formatTime(game.getGameConfig().getPvpTime()));
        ItemStack episodeTime = createItem(Material.WATCH, "&e● &fTemps par Episode", "", "&7❱ " + formatTime(game.getGameConfig().getEpisodeTime()));
        ItemStack roleAnnounce = createItem(Material.WATCH, "&e● &fAnnonce des Roles", "", "&7❱ " + formatTime(game.getGameConfig().getRoleAssignTime()));
        ItemStack eventTime = createItem(Material.WATCH, "&e● &fTemps avant l'Event", "", "&7❱ " + formatTime(game.getGameConfig().getEventTime()));
        ItemStack arrowItem = createItem(Material.ARROW, "§aSauvegarder", "", "§7❱ Clique pour sauvegarder");

        inventory.setItem(49, arrowItem);
        inventory.setItem(11, timeCycle);
        inventory.setItem(12, timePvp);
        inventory.setItem(14, episodeTime);
        inventory.setItem(15, roleAnnounce);
        inventory.setItem(22, eventTime);

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
        if (Objects.requireNonNull(clickedItem.getType()) == Material.WATCH) {
            String displayName = clickedItem.getItemMeta().getDisplayName();
            if (displayName.contains("Cycle de Jour/Nuit")) {
                updateTime(event.getClick(), game.getGameConfig()::getCycleTime, game.getGameConfig()::setCycleTime);
            } else if (displayName.contains("Temps avant le PvP")) {
                updateTime(event.getClick(), game.getGameConfig()::getPvpTime, game.getGameConfig()::setPvpTime);
            } else if (displayName.contains("Temps par Episode")) {
                updateTime(event.getClick(), game.getGameConfig()::getEpisodeTime, game.getGameConfig()::setEpisodeTime);
            } else if (displayName.contains("Annonce des Roles")) {
                updateTime(event.getClick(), game.getGameConfig()::getRoleAssignTime, game.getGameConfig()::setRoleAssignTime);
            } else if (displayName.contains("Temps avant l'Event")) {
                updateTime(event.getClick(), game.getGameConfig()::getEventTime, game.getGameConfig()::setEventTime);
            }
            open(player);
        }
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    private void updateTime(ClickType clickType, java.util.function.Supplier<Long> getter, java.util.function.Consumer<Long> setter) {
        long time = getter.get();
        if (clickType == ClickType.LEFT) {
            setter.accept(incrementTime(time));
        } else if (clickType == ClickType.RIGHT) {
            setter.accept(decrementTime(time));
        }
    }

    private long decrementTime(long time) {
        return Math.max(0, time - 300);
    }

    private long incrementTime(long time) {
        return time + 300;
    }

    private String formatTime(long time) {
        long hours = time / 3600;
        long minutes = (time % 3600) / 60;
        return String.format("%02dh%02d", hours, minutes);
    }
}