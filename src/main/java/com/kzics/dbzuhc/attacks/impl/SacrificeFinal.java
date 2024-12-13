package com.kzics.dbzuhc.attacks.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.attacks.Attack;
import com.kzics.dbzuhc.manager.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SacrificeFinal implements Attack {
    private static final long COOLDOWN = 5 * 60 * 1000; // 5 minutes
    private static final long DAMAGE_INTERVAL = 15 * 20L; // 15 secondes en ticks
    private static final long MAX_DURATION = 3 * 60 * 20L; // 3 minutes en ticks
    private final CooldownManager cooldownManager = new CooldownManager();
    private final Set<UUID> usedDuringTransformation = new HashSet<>();

    @Override
    public String getName() {
        return "Sacrifice Final";
    }

    @Override
    public void execute(Player attacker) {
        UUID attackerId = attacker.getUniqueId();

        // Vérifie le cooldown global
        if (!cooldownManager.isCooldownReady(attackerId, getName())) {
            attacker.sendMessage("§cSacrifice Final est en recharge !");
            return;
        }

        // Vérifie si l'attaque a déjà été utilisée pendant cette transformation
        if (usedDuringTransformation.contains(attackerId)) {
            attacker.sendMessage("§cVous ne pouvez utiliser Sacrifice Final qu'une seule fois par transformation !");
            return;
        }

        // Active le cooldown et marque l'attaque comme utilisée pour cette transformation
        cooldownManager.setCooldown(attackerId, getName(), COOLDOWN);
        usedDuringTransformation.add(attackerId);

        // Trouver les ennemis dans un rayon de 30 blocs
        List<Entity> enemies = attacker.getWorld().getEntities().stream()
                .filter(p -> p != attacker && p.getLocation().distance(attacker.getLocation()) <= 30)
                .collect(Collectors.toList());

        if (enemies.size() < 2) {
            attacker.sendMessage("§cPas assez d'ennemis à proximité pour utiliser Sacrifice Final.");
            return;
        }

        // Sélectionner deux ennemis au hasard
        Entity enemy1 = enemies.get(0);
        Entity enemy2 = enemies.get(1);

        // Enfermer les joueurs dans une cage et infliger des dégâts périodiques
        Location cageCenter = attacker.getLocation();
        Set<Entity> playersInCage = new HashSet<>();
        playersInCage.add(attacker);
        playersInCage.add(enemy1);
        playersInCage.add(enemy2);

        // Téléporter les joueurs à côté du lanceur
        Vector offset = new Vector(2, 0, 2);
        attacker.teleport(cageCenter.clone().add(offset));
        enemy1.teleport(cageCenter.clone().subtract(offset));
        enemy2.teleport(cageCenter.clone().add(offset.multiply(-1)));

        // Créer la cage
        createCage(cageCenter);

        new BukkitRunnable() {
            private long ticks = 0;

            @Override
            public void run() {
                if (ticks >= MAX_DURATION) {
                    // Fin du temps imparti, téléportation des joueurs à leur position initiale
                    playersInCage.forEach(p -> p.teleport(cageCenter));
                    attacker.sendMessage("§eLe temps est écoulé, tout le monde est téléporté à sa position initiale.");
                    cancel();
                    return;
                }

                if (ticks % DAMAGE_INTERVAL == 0) {
                    // Infliger des dégâts périodiques
                    //playersInCage.forEach(p -> p(1.0, attacker)); // 0.5 cœur = 1.0 dégâts
                }

                // Vérifier si Majin Végéta ou les deux ennemis sont morts
                if (attacker.isDead() || (enemy1.isDead() && enemy2.isDead())) {
                    // Fin du combat, téléportation des joueurs à leur position initiale
                    playersInCage.forEach(p -> p.teleport(cageCenter));
                    attacker.sendMessage("§eFin du combat, tout le monde est téléporté à sa position initiale.");
                    cancel();
                }

                ticks++;
            }
        }.runTaskTimer(DbzUHC.getInstance(), 0L, 1L); // Exécution toutes les 1 tick (50 ms)
    }

    @Override
    public boolean canExecute(UUID attacker) {
        return cooldownManager.isCooldownReady(attacker, getName());
    }

    private void createCage(Location center) {
        int size = 5; // Taille de la cage (5x5x5)

        for (int x = -size / 2; x <= size / 2; x++) {
            for (int y = -size / 2; y <= size / 2; y++) {
                for (int z = -size / 2; z <= size / 2; z++) {
                    // Ajouter les blocs seulement aux bords de la cage
                    if (Math.abs(x) == size / 2 || Math.abs(y) == size / 2 || Math.abs(z) == size / 2) {
                        center.getWorld().getBlockAt(center.clone().add(x, y, z)).setType(Material.BARRIER);
                    }
                }
            }
        }
    }


    public void resetDuringTransformation(UUID playerId) {
        usedDuringTransformation.remove(playerId);
    }
}
