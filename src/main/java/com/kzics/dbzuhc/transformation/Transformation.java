package com.kzics.dbzuhc.transformation;

import com.kzics.dbzuhc.attacks.Attack;
import org.bukkit.entity.Player;

public interface Transformation {
    String getName();

    void applyEffects(Player player);

    void onActivate(Player player);

    void onDeactivate(Player player);

    boolean canActivate(Player player);

    Attack getAttack(); // Chaque transformation possède une attaque

    int getDuration(); // Durée en secondes avant la détransformation
}
