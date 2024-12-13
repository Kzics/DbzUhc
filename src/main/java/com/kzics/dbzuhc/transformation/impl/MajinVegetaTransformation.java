package com.kzics.dbzuhc.transformation.impl;

import com.kzics.dbzuhc.attacks.Attack;
import com.kzics.dbzuhc.attacks.impl.BigBangAttack;
import com.kzics.dbzuhc.transformation.Transformation;
import org.bukkit.entity.Player;

public class MajinVegetaTransformation implements Transformation {
    @Override
    public String getName() {
        return "Majin Vegeta";
    }

    @Override
    public void applyEffects(Player player) {

    }

    @Override
    public void onActivate(Player player) {
        player.sendMessage("§bMajin Vegeta activé !");
    }

    @Override
    public void onDeactivate(Player player) {
        player.sendMessage("§cMajin Vegeta désactivé.");

    }

    @Override
    public boolean canActivate(Player player) {
        return false;
    }

    @Override
    public Attack getAttack() {
        return new BigBangAttack();
    }

    @Override
    public int getDuration() {
        return 0;
    }
}
