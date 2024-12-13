package com.kzics.dbzuhc.roles.impl;

import com.kzics.dbzuhc.game.UHCGame;
import com.kzics.dbzuhc.player.PlayerData;
import com.kzics.dbzuhc.roles.AdvancedRole;
import com.kzics.dbzuhc.teams.CampType;
import com.kzics.dbzuhc.transformation.Transformation;

import java.util.Collections;
import java.util.List;

public class GokuRole extends AdvancedRole {
    public GokuRole() {
        super("Goku");
    }

    @Override
    public void onKill(UHCGame game, PlayerData killer, PlayerData victim) {
        game.getGameConfig().removeRole(victim.getRole());


    }

    @Override
    public List<Transformation> getTransformations() {
        return Collections.emptyList();
    }

    @Override
    public CampType getCampType() {
        return CampType.UNIVERSE7;
    }
}
