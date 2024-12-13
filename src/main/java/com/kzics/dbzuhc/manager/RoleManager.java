package com.kzics.dbzuhc.manager;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.roles.AdvancedRole;
import com.kzics.dbzuhc.roles.impl.GokuRole;
import com.kzics.dbzuhc.roles.impl.VegetaRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoleManager {
    private final HashMap<String, AdvancedRole> roles = new HashMap<>();

    public RoleManager(DbzUHC plugin) {
        loadRoles();
    }

    public void loadRoles() {
        registerRole(new GokuRole());
        registerRole(new VegetaRole());
    }

    public void registerRole(AdvancedRole role) {
        roles.put(role.getName(), role);
    }

    public AdvancedRole getRole(String name) {
        return roles.get(name);
    }

    public List<AdvancedRole> getAllRoles() {
        return new ArrayList<>(roles.values());
    }
}
