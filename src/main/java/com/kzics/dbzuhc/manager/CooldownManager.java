package com.kzics.dbzuhc.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    // Map contenant les cooldowns (clé : UUID du joueur, action, valeur : temps de fin du cooldown)
    private final Map<String, Long> cooldowns = new HashMap<>();

    /**
     * Vérifie si une action spécifique est prête pour un joueur donné.
     *
     * @param playerId L'UUID du joueur.
     * @param action   Le nom de l'action.
     * @return true si l'action peut être exécutée, sinon false.
     */
    public boolean isCooldownReady(UUID playerId, String action) {
        String key = generateKey(playerId, action);
        return !cooldowns.containsKey(key) || System.currentTimeMillis() >= cooldowns.get(key);
    }

    /**
     * Définit un cooldown pour une action spécifique d'un joueur.
     *
     * @param playerId L'UUID du joueur.
     * @param action   Le nom de l'action.
     * @param cooldown La durée du cooldown en millisecondes.
     */
    public void setCooldown(UUID playerId, String action, long cooldown) {
        String key = generateKey(playerId, action);
        cooldowns.put(key, System.currentTimeMillis() + cooldown);
    }

    /**
     * Récupère le temps restant avant la fin du cooldown pour une action spécifique.
     *
     * @param playerId L'UUID du joueur.
     * @param action   Le nom de l'action.
     * @return Le temps restant en millisecondes, ou 0 si aucun cooldown n'est actif.
     */
    public long getRemainingCooldown(UUID playerId, String action) {
        String key = generateKey(playerId, action);
        if (!cooldowns.containsKey(key)) {
            return 0;
        }
        return Math.max(0, cooldowns.get(key) - System.currentTimeMillis());
    }

    /**
     * Réinitialise un cooldown pour une action spécifique d'un joueur.
     *
     * @param playerId L'UUID du joueur.
     * @param action   Le nom de l'action.
     */
    public void resetCooldown(UUID playerId, String action) {
        String key = generateKey(playerId, action);
        cooldowns.remove(key);
    }

    /**
     * Génère une clé unique pour identifier une combinaison joueur/action.
     *
     * @param playerId L'UUID du joueur.
     * @param action   Le nom de l'action.
     * @return Une chaîne unique correspondant à cette combinaison.
     */
    private String generateKey(UUID playerId, String action) {
        return playerId.toString() + ":" + action;
    }
}
