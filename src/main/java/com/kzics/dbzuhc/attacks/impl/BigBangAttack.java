package com.kzics.dbzuhc.attacks.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.attacks.Attack;
import com.kzics.dbzuhc.manager.CooldownManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BigBangAttack implements Attack {
    private static final long COOLDOWN = 5 * 60 * 1000; // 5 minutes
    private static final double SPHERE_RADIUS = 1.5; // Rayon de la sphère
    private static final int MAX_TICKS = 60; // Durée maximale de vie de la boule (en ticks)
    private final CooldownManager cooldownManager = new CooldownManager();
    private final Set<UUID> usedDuringTransformation = new HashSet<>();

    @Override
    public String getName() {
        return "Big Bang Attack";
    }

    @Override
    public void execute(Player attacker) {
        UUID attackerId = attacker.getUniqueId();

        // Vérifie le cooldown global
        if (!cooldownManager.isCooldownReady(attackerId, getName())) {
            attacker.sendMessage("§cBig Bang Attack est en recharge !");
            return;
        }

        // Vérifie si l'attaque a déjà été utilisée pendant cette transformation
        if (usedDuringTransformation.contains(attackerId)) {
            attacker.sendMessage("§cVous ne pouvez utiliser Big Bang Attack qu'une seule fois par transformation !");
            return;
        }

        cooldownManager.setCooldown(attackerId, getName(), COOLDOWN);
        usedDuringTransformation.add(attackerId);

        // Lancer l'attaque
        attacker.sendMessage("§bBig Bang Attack lancé !");
        Location startLocation = attacker.getEyeLocation();
        Vector direction = startLocation.getDirection().normalize().multiply(0.5);

        new BukkitRunnable() {
            private int ticks = 0;
            private final Location currentLocation = startLocation.clone();

            @Override
            public void run() {
                if (ticks >= MAX_TICKS) {
                    cancel();
                    return;
                }

                currentLocation.add(direction);

                for (double phi = 0; phi <= Math.PI; phi += Math.PI / 10) {
                    for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 10) {
                        double x = SPHERE_RADIUS * Math.sin(phi) * Math.cos(theta);
                        double y = SPHERE_RADIUS * Math.sin(phi) * Math.sin(theta);
                        double z = SPHERE_RADIUS * Math.cos(phi);

                        Location particleLocation = currentLocation.clone().add(x, y, z);
                        sendParticle(attacker, particleLocation, 0, 0, 255); // RGB pour bleu
                    }
                }

                currentLocation.getWorld().playSound(currentLocation, Sound.FIREWORK_LAUNCH, 0.5f, 1.0f);

                if (currentLocation.getBlock().getType().isSolid()) {
                    explode(currentLocation, attacker);
                    cancel();
                    return;
                }

                // Vérifie si la boule touche une entité
                for (Entity entity : currentLocation.getWorld().getNearbyEntities(currentLocation, SPHERE_RADIUS, SPHERE_RADIUS, SPHERE_RADIUS)) {
                    if (entity instanceof Player && !entity.getUniqueId().equals(attackerId)) {
                        explode(currentLocation, attacker);
                        cancel();
                        return;
                    }
                }

                ticks++;
            }
        }.runTaskTimer(DbzUHC.getInstance(), 0L, 1L); // Déplacement toutes les 1 tick (50 ms)
    }

    @Override
    public boolean canExecute(UUID attacker) {
        return cooldownManager.isCooldownReady(attacker, getName());
    }

    private void explode(Location location, Player attacker) {
        // Explosion sans dégâts aux blocs
        location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 0, false, false);

        // Applique des dégâts aux entités proches
        for (Entity entity : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (!target.getUniqueId().equals(attacker.getUniqueId())) {
                    target.damage(4.0, attacker); // 4.0 = 2 cœurs de dégâts
                }
            }
        }

        // Émet un son d'explosion
        location.getWorld().playSound(location, Sound.EXPLODE, 1.0f, 1.0f);
    }

    public void resetDuringTransformation(UUID playerId) {
        usedDuringTransformation.remove(playerId);
    }

    private void sendParticle(Player player, Location location, float r, float g, float b) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                EnumParticle.REDSTONE, true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                r / 255, g / 255, b / 255, 1, 0);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
