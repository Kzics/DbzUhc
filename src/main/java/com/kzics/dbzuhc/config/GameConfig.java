package com.kzics.dbzuhc.config;

import com.kzics.dbzuhc.roles.AdvancedRole;

import java.util.ArrayList;
import java.util.List;

public class GameConfig {
    private final List<AdvancedRole> enabledRoles = new ArrayList<>();

    private long cycleTime = 0;
    private long pvpTime = 0;
    private long episodeTime = 0;
    private long roleAssignTime = 0;
    private long eventTime = 0;
    private int mapSize = 1000;

    public long getCycleTime() {
        return cycleTime;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int size) {
        this.mapSize = size;
    }

    public void setCycleTime(long cycleTime) {
        this.cycleTime = cycleTime;
    }

    public long getPvpTime() {
        return pvpTime;
    }

    public void setPvpTime(long pvpTime) {
        this.pvpTime = pvpTime;
    }

    public long getEpisodeTime() {
        return episodeTime;
    }

    public void setEpisodeTime(long episodeTime) {
        this.episodeTime = episodeTime;
    }

    public long getRoleAssignTime() {
        return roleAssignTime;
    }

    public void setRoleAssignTime(long roleAssignTime) {
        this.roleAssignTime = roleAssignTime;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public boolean isRoleEnabled(AdvancedRole role) {
        return enabledRoles.contains(role);
    }

    public void enableRole(AdvancedRole role) {
        enabledRoles.add(role);
    }

    public void disableRole(AdvancedRole role) {
        enabledRoles.remove(role);
    }

    public List<AdvancedRole> getEnabledRoles() {
        return new ArrayList<>(enabledRoles);
    }

    public void removeRole(AdvancedRole role) {
        enabledRoles.remove(role);
    }
}