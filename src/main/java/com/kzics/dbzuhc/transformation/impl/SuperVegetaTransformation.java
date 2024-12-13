package com.kzics.dbzuhc.transformation.impl;

import com.kzics.dbzuhc.attacks.Attack;
import com.kzics.dbzuhc.attacks.impl.BigBangAttack;
import com.kzics.dbzuhc.attacks.impl.SacrificeFinal;
import com.kzics.dbzuhc.transformation.Transformation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuperVegetaTransformation implements Transformation {
    private final Attack attack;

    public SuperVegetaTransformation() {
        this.attack = new SacrificeFinal();
    }

    @Override
    public String getName() {
        return "Super Vegeta 1";
    }

    @Override
    public void applyEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * getDuration(), 0)); // Force 1
    }

    @Override
    public void onActivate(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * getDuration(), 0)); // Force 1
        player.sendMessage("§bSuper Vegeta activé !");

        Bukkit.getScheduler().runTaskLaterAsynchronously(Bukkit.getPluginManager().getPlugin("DbzUHC"), () -> {
            if (player.isOnline()) {
                player.sendMessage("§cSuper Vegeta désactivé.");
                onDeactivate(player);
            }
        }, 20L * getDuration());
    }

    @Override
    public void onDeactivate(Player player) {
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

    }

    @Override
    public boolean canActivate(Player player) {
        return true;
    }

    @Override
    public Attack getAttack() {
        return attack;
    }

    @Override
    public int getDuration() {
        return 180; // 3 minutes
    }
}
