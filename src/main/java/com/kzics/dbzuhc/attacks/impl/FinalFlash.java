package com.kzics.dbzuhc.attacks.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.attacks.Attack;
import com.kzics.dbzuhc.manager.CooldownManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FinalFlash implements Attack {
    private static final long COOLDOWN = 15 * 60 * 1000; // 15 minutes
    private static final long IMMOBILIZE_DURATION = 3 * 20L; // 3 secondes en ticks
    private static final double BEAM_DAMAGE = 6.0; // 3 cœurs de dégâts
    private static final double SPHERE_RADIUS = 1.5; // Rayon de la sphère
    private static final int MAX_TICKS = 60; // Durée maximale de vie de la sphère (en ticks)
    private final CooldownManager cooldownManager = new CooldownManager();
    private final Set<UUID> usedDuringTransformation = new HashSet<>();

    @Override
    public String getName() {
        return "Final Flash";
    }

    @Override
    public void execute(Player attacker) {
        UUID attackerId = attacker.getUniqueId();

        // Vérifie le cooldown global
        if (!cooldownManager.isCooldownReady(attackerId, getName())) {
            attacker.sendMessage("§cFinal Flash est en recharge !");
            return;
        }

        if (usedDuringTransformation.contains(attackerId)) {
            attacker.sendMessage("§cVous ne pouvez utiliser Final Flash qu'une seule fois par transformation !");
            return;
        }

        cooldownManager.setCooldown(attackerId, getName(), COOLDOWN);
        usedDuringTransformation.add(attackerId);

        attacker.sendMessage("§bConcentration de l'énergie...");
        attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) IMMOBILIZE_DURATION, 10, false, false));

        new BukkitRunnable() {
            @Override
            public void run() {
                Location startLocation = attacker.getEyeLocation();
                Vector direction = startLocation.getDirection().normalize().multiply(0.5);

                new BukkitRunnable() {
                    private final Location currentLocation = startLocation.clone();
                    private int ticks = 0;

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
                                sendParticle(attacker, particleLocation, 255, 255, 0); // RGB pour jaune

                                if (particleLocation.getBlock().getType() != Material.AIR) {
                                    particleLocation.getBlock().setType(Material.AIR);
                                }

                                for (Entity entity : particleLocation.getWorld().getNearbyEntities(particleLocation, 1, 1, 1)) {
                                    if (entity instanceof Player && !entity.getUniqueId().equals(attackerId)) {
                                        ((Player) entity).damage(BEAM_DAMAGE, attacker);
                                    }
                                }
                            }
                        }

                        ticks++;
                    }
                }.runTaskTimer(DbzUHC.getInstance(), 0L, 1L);
            }
        }.runTaskLater(DbzUHC.getInstance(), IMMOBILIZE_DURATION);
    }

    @Override
    public boolean canExecute(UUID attacker) {
        return cooldownManager.isCooldownReady(attacker, getName());
    }

    private void sendParticle(Player player, Location location, float r, float g, float b) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                EnumParticle.REDSTONE, true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                r / 255, g / 255, b / 255, 1, 0);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void resetDuringTransformation(UUID playerId) {
        usedDuringTransformation.remove(playerId);
    }
}
