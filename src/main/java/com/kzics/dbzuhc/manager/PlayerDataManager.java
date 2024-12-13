package com.kzics.dbzuhc.manager;

import com.kzics.dbzuhc.player.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private final HashMap<UUID, PlayerData> players = new HashMap<>();
    public PlayerDataManager() {
    }

    public PlayerData getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void addPlayer(UUID uuid, PlayerData playerData) {
        players.put(uuid, playerData);
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    public HashMap<UUID, PlayerData> getPlayers() {
        return players;
    }
}
