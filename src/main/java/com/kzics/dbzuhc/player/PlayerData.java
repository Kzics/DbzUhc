package com.kzics.dbzuhc.player;

import com.kzics.dbzuhc.roles.AdvancedRole;
import com.kzics.dbzuhc.transformation.Transformation;

import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private Transformation currentTransformation;
    private AdvancedRole role;
    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public Transformation getCurrentTransformation() {
        return currentTransformation;
    }

    public void setCurrentTransformation(Transformation currentTransformation) {
        this.currentTransformation = currentTransformation;
    }

    public AdvancedRole getRole() {
        return role;
    }

    public void setRole(AdvancedRole role) {
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }
}
