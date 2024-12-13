package com.kzics.dbzuhc.manager;

import com.kzics.dbzuhc.game.UHCGame;
import org.bukkit.inventory.ItemStack;

public class GameManager {
    private ItemStack[] startingItems;
    private final UHCGame game;

    public GameManager() {
        this.game = new UHCGame();
    }
    public void setStartingItems(ItemStack[] startingItems) {
        this.startingItems = startingItems;
    }

    public UHCGame getGame() {
        return game;
    }

    public ItemStack[] getStartingItems() {
        return startingItems;
    }
}