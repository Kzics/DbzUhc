package com.kzics.dbzuhc.menu.impl;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.game.UHCGame;
import com.kzics.dbzuhc.manager.RoleManager;
import com.kzics.dbzuhc.menu.UHCMenu;
import com.kzics.dbzuhc.roles.AdvancedRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RolesMenu extends UHCMenu {
    private final RoleManager roleManager;

    public RolesMenu(UHCGame game, RoleManager roleManager) {
        super(game);
        this.roleManager = roleManager;
    }

    @Override
    public void open(Player player) {
        this.addBorder();

        ItemStack arrowItem = createItem(Material.ARROW, "§aSauvegarder", "", "§7❱ Clique pour sauvegarder");
        inventory.setItem(49, arrowItem);
        int index = 10;
        for (AdvancedRole role : roleManager.getAllRoles()) {
            ItemStack roleItem = createRoleItem(role);
            inventory.setItem(index, roleItem);
            index++;
        }
        player.openInventory(inventory);
    }

    private ItemStack createRoleItem(AdvancedRole role) {
        Material material = game.getGameConfig().isRoleEnabled(role) ? Material.SLIME_BALL : Material.SULPHUR;
        String displayName = game.getGameConfig().isRoleEnabled(role) ? "§a" + role.getName() : "§c" + role.getName();
        return createItem(material, displayName, "&7❱ Cliquez pour activer/désactiver");
    }

    @Override
    public void handle(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        if(clickedItem.getType() == Material.ARROW) {
            new MainConfigMenu(game, DbzUHC.getInstance().getRoleManager()).open((Player) event.getWhoClicked());
            return;
        }
        Player player = (Player) event.getWhoClicked();
        String roleName = clickedItem.getItemMeta().getDisplayName().substring(2);
        AdvancedRole role = roleManager.getRole(roleName);

        if (game.getGameConfig().isRoleEnabled(role)) {
            game.getGameConfig().disableRole(role);
            player.sendMessage("§c" + roleName + " désactivé");

        } else {
            game.getGameConfig().enableRole(role);
            player.sendMessage("§a" + roleName + " activé");
        }

        open(player);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}