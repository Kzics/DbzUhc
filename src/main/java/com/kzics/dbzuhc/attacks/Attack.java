package com.kzics.dbzuhc.attacks;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface Attack {
    String getName();

    void execute(Player attacker);

    boolean canExecute(UUID attacker); // Vérifie si l'attaque peut être effectuée (ex : cooldown)
}
