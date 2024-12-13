package com.kzics.dbzuhc.attacks.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.attacks.Attack;
import com.kzics.dbzuhc.manager.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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

        if (!cooldownManager.isCooldownReady(attackerId, getName())) {
            attacker.sendMessage("§cSacrifice Final est en recharge !");
            return;
        }

        if (usedDuringTransformation.contains(attackerId)) {
            attacker.sendMessage("§cVous ne pouvez utiliser Sacrifice Final qu'une seule fois par transformation !");
            return;
        }

        cooldownManager.setCooldown(attackerId, getName(), COOLDOWN);
        usedDuringTransformation.add(attackerId);

        List<LivingEntity> enemies = attacker.getWorld().getLivingEntities().stream()
                .filter(p -> p != attacker && p.getLocation().distance(attacker.getLocation()) <= 30)
                .collect(Collectors.toList());

        if (enemies.size() < 2) {
            attacker.sendMessage("§cPas assez d'ennemis à proximité pour utiliser Sacrifice Final.");
            return;
        }

        LivingEntity enemy1 = enemies.get(0);
        LivingEntity enemy2 = enemies.get(1);

        Location cageCenter = attacker.getLocation();
        Set<LivingEntity> playersInCage = new HashSet<>();
        playersInCage.add(attacker);
        playersInCage.add(enemy1);
        playersInCage.add(enemy2);

        Vector offset = new Vector(2, 0, 2);
        attacker.teleport(cageCenter.clone().add(offset));
        enemy1.teleport(cageCenter.clone().subtract(offset));
        enemy2.teleport(cageCenter.clone().add(offset.multiply(-1)));

        createCage(cageCenter);

        new BukkitRunnable() {
            private long ticks = 0;

            @Override
            public void run() {
                if (ticks >= MAX_DURATION) {
                    playersInCage.forEach(p -> p.teleport(cageCenter));
                    attacker.sendMessage("§eLe temps est écoulé, tout le monde est téléporté à sa position initiale.");
                    cancel();
                    return;
                }

                if (ticks % DAMAGE_INTERVAL == 0) {
                    playersInCage.forEach(p -> p.damage(1.0, attacker)); // 0.5 cœur = 1.0 dégâts
                }

                if (attacker.isDead() || (enemy1.isDead() && enemy2.isDead())) {
                    playersInCage.forEach(p -> p.teleport(cageCenter));
                    attacker.sendMessage("§eFin du combat, tout le monde est téléporté à sa position initiale.");
                    cancel();
                }

                ticks++;
            }
        }.runTaskTimer(DbzUHC.getInstance(), 0L, 1L);
    }

    @Override
    public boolean canExecute(UUID attacker) {
        return cooldownManager.isCooldownReady(attacker, getName());
    }

    private void createCage(Location center) {
        int size = 15;

        for (int x = -size / 2; x <= size / 2; x++) {
            for (int y = -size / 2; y <= size / 2; y++) {
                for (int z = -size / 2; z <= size / 2; z++) {
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
