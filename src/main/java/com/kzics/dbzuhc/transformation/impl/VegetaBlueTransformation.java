package com.kzics.dbzuhc.transformation.impl;

import com.kzics.dbzuhc.attacks.Attack;
import com.kzics.dbzuhc.attacks.impl.FinalFlash;
import com.kzics.dbzuhc.transformation.Transformation;
import org.bukkit.entity.Player;

public class VegetaBlueTransformation implements Transformation {
    @Override
    public String getName() {
        return "Vegeta Blue";
    }

    @Override
    public void applyEffects(Player player) {

    }

    @Override
    public void onActivate(Player player) {

    }

    @Override
    public void onDeactivate(Player player) {

    }

    @Override
    public boolean canActivate(Player player) {
        return false;
    }

    @Override
    public Attack getAttack() {
        return new FinalFlash();
    }

    @Override
    public int getDuration() {
        return 0;
    }
}
