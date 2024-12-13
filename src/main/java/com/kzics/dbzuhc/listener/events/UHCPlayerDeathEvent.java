package com.kzics.dbzuhc.listener.events;

import com.kzics.dbzuhc.player.PlayerData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UHCPlayerDeathEvent extends Event implements Cancellable  {
    public static HandlerList hList = new HandlerList();
    private final PlayerData killer;
    private final PlayerData victim;
    public UHCPlayerDeathEvent( PlayerData killer, PlayerData victim) {
        this.killer = killer;
        this.victim = victim;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    public PlayerData getVictim() {
        return victim;
    }

    public PlayerData getKiller() {
        return killer;
    }

    public static HandlerList getHandlerList() {
        return hList;
    }

    @Override
    public HandlerList getHandlers() {
        return hList;
    }
}
