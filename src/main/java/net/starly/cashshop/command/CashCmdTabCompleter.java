package net.starly.cashshop.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class CashCmdTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            return sender.isOp() ? List.of("확인", "추가", "제거", "설정", "초기화", "주기") : List.of("확인", "주기");
        } else if (args.length == 2) {
            if (List.of("확인", "check", "추가", "add", "제거", "remove", "설정", "set", "초기화", "reset").contains(args[0].toLowerCase()) && sender.isOp()) return null;
            else if (List.of("주기", "give").contains(args[0].toLowerCase()) && sender.isOp()) return null;
        }

        return Collections.emptyList();
    }
}
