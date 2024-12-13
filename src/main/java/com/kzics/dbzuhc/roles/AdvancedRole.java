package com.kzics.dbzuhc.roles;

import com.kzics.dbzuhc.game.UHCGame;
import com.kzics.dbzuhc.manager.RoleManager;
import com.kzics.dbzuhc.player.PlayerData;
import com.kzics.dbzuhc.teams.CampType;
import com.kzics.dbzuhc.transformation.Transformation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AdvancedRole {
    private final String name;
    private CampType campType;
    protected final List<Transformation> transformations = new ArrayList<>();
    protected AdvancedRole(String name) {
        this.name = name;
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    public CampType getCampType() {
        return campType;
    }

    public String getName() {
        return name;
    }

    public void onAttack(PlayerData attack, Transformation transformation) {}

    public void onKill(UHCGame game, PlayerData killer, PlayerData victim){}

    public void onTransformation(PlayerData player, Transformation transformation) {}


    public void onGameStart(Player player) {
    }

    public void onGameEnd(Player player) {
    }
}