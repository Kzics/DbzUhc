package com.kzics.dbzuhc;

import com.kzics.dbzuhc.command.dbz.DBZCommand;
import com.kzics.dbzuhc.listener.InventoryListeners;
import com.kzics.dbzuhc.listener.PlayerListeners;
import com.kzics.dbzuhc.manager.GameManager;
import com.kzics.dbzuhc.manager.PlayerDataManager;
import com.kzics.dbzuhc.manager.RoleManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DbzUHC extends JavaPlugin {


    private GameManager gameManager;
    private RoleManager roleManager;
    private PlayerDataManager playerDataManager;
    private static DbzUHC instance;

    private void registerCommands() {
        getCommand("dbzuhc").setExecutor(new DBZCommand(this));
    }

    private void registerListeners() {
        new PlayerListeners(this);
        getServer().getPluginManager().registerEvents(new InventoryListeners(), this);
    }

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        gameManager = new GameManager();
        roleManager = new RoleManager(this);
        playerDataManager = new PlayerDataManager();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }

    public static DbzUHC getInstance() {
        return instance;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
