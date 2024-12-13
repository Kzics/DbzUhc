package com.kzics.dbzuhc.game;

import com.kzics.dbzuhc.config.GameConfig;
import org.bukkit.inventory.ItemStack;

public class UHCGame {

    private final GameConfig gameConfig;
    private ItemStack[] startingItems;


    public UHCGame() {
        this.gameConfig = new GameConfig();
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void setStartingItems(ItemStack[] startingItems) {
        this.startingItems = startingItems;
    }

    public ItemStack[] getStartingItems() {
        return startingItems;
    }
}