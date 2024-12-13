package com.kzics.dbzuhc.command.dbz;

import com.kzics.dbzuhc.DbzUHC;
import com.kzics.dbzuhc.command.CommandBase;
import com.kzics.dbzuhc.menu.impl.MainConfigMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DBZCommand extends CommandBase {
    public DBZCommand(DbzUHC managerHandler) {
        super(managerHandler);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        new MainConfigMenu(managerHandler.getGameManager().getGame(), managerHandler.getRoleManager()).open((Player) sender);
        return true;
    }
}
