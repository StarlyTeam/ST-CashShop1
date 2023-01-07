package net.starly.cashshop.command;

import net.starly.cashshop.gui.CashShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CashShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) new CashShop("default-shop", (Player) sender).openEditor();
        else if (args.length == 1) new CashShop("default-shop", (Player) sender).openShop();

        return false;
    }
}
