package net.starly.cashshop.command;

import net.starly.cashshop.api.CashAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

import static net.starly.cashshop.CashShopMain.config;

public class CashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(config.getMessage("messages.command.console"));
            return true;
        }

        CashAPI cashApi = new CashAPI();
        if (args.length == 0) {
            p.sendMessage(config.getMessage("messages.command.check.self", Map.of(
                    "{player}", p.getDisplayName(),
                    "{cash}", cashApi.getCash(p).toString()
            )));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "확인", "check" -> {
                if (args.length == 1) {
                    if (!p.hasPermission("cashshop.command.check.self")) {
                        p.sendMessage(config.getMessage("messages.command.no_permission"));
                        return false;
                    }

                    p.sendMessage(config.getMessage("messages.command.check.self", Map.of(
                            "{player}", p.getDisplayName(),
                            "{cash}", cashApi.getCash(p).toString()
                    )));
                    return true;
                } else if (args.length == 2) {
                    if (!p.hasPermission("cashshop.command.check.other")) {
                        p.sendMessage(config.getMessage("messages.command.no_permission"));
                        return false;
                    }

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(config.getMessage("messages.command.no_player", Map.of(
                                "{executor}", p.getDisplayName(),
                                "{target}", args[1]
                        )));
                        return false;
                    }

                    p.sendMessage(config.getMessage("messages.command.check.other", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", target.getDisplayName(),
                            "{cash}", cashApi.getCash(target).toString()
                    )));
                    return true;
                }

                p.sendMessage(config.getMessage("messages.command.wrong_command"));
                return false;
            }

            case "추가", "add" -> {
                if (!p.hasPermission("starly.cashshop.cash.add")) {
                    p.sendMessage(config.getMessage("messages.command.no_permission"));
                    return false;
                }

                if (args.length != 3) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(config.getMessage("messages.command.no_player", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", args[1]
                    )));
                    return false;
                }

                Integer currentCash = null;
                try {
                    currentCash = Integer.parseInt(args[2]);
                } catch (Exception ex) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                if (currentCash < 0) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                try {
                    cashApi.addCash(target, currentCash);
                } catch (NumberFormatException e) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                p.sendMessage(config.getMessage("messages.command.add.executor", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName(),
                        "{cash}", currentCash.toString()
                )));
                target.sendMessage(config.getMessage("messages.command.add.target", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName(),
                        "{cash}", currentCash.toString()
                )));
                return true;
            }

            case "제거", "remove" -> {
                if (!p.hasPermission("starly.cashshop.cash.remove")) {
                    p.sendMessage(config.getMessage("messages.command.no_permission"));
                    return false;
                }

                if (args.length != 3) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(config.getMessage("messages.command.no_player", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", args[1]
                    )));
                    return false;
                }

                Integer currentCash = null;
                try {
                    currentCash = Integer.parseInt(args[2]);
                } catch (Exception ex) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                if (currentCash < 0) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                if (cashApi.getCash(target) < currentCash) {
                    p.sendMessage(config.getMessage("messages.command.remove.no_balance", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", target.getDisplayName(),
                            "{cash}", currentCash.toString(),
                            "{balance}", cashApi.getCash(target).toString()
                    )));
                    return false;
                }

                try {
                    cashApi.removeCash(target, currentCash);
                } catch (NumberFormatException e) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                p.sendMessage(config.getMessage("messages.command.remove.executor", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName(),
                        "{cash}", currentCash.toString()
                )));
                target.sendMessage(config.getMessage("messages.command.remove.target", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName(),
                        "{cash}", currentCash.toString()
                )));
                return true;
            }

            case "설정", "set" -> {
                if (!p.hasPermission("starly.cashshop.cash.set")) {
                    p.sendMessage(config.getMessage("messages.command.no_permission"));
                    return false;
                }

                if (args.length != 3) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(config.getMessage("messages.command.no_player", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", args[1]
                    )));
                    return false;
                }

                Integer currentCash = null;
                try {
                    currentCash = Integer.parseInt(args[2]);
                } catch (Exception ex) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                if (currentCash < 0) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                try {
                    cashApi.setCash(target, currentCash);
                } catch (NumberFormatException e) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                p.sendMessage(config.getMessage("messages.command.set.executor", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName(),
                        "{cash}", currentCash.toString()
                )));
                target.sendMessage(config.getMessage("messages.command.set.target", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName(),
                        "{cash}", currentCash.toString()
                )));
                return true;
            }

            case "초기화", "reset" -> {
                if (!p.hasPermission("starly.cashshop.cash.reset")) {
                    p.sendMessage(config.getMessage("messages.command.no_permission"));
                    return false;
                }

                if (args.length != 2) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(config.getMessage("messages.command.no_player", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", args[1]
                    )));
                    return false;
                }

                cashApi.resetCash(target);

                p.sendMessage(config.getMessage("messages.command.reset.executor", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName()
                )));
                target.sendMessage(config.getMessage("messages.command.reset.target", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName()
                )));
                return true;
            }

            case "주기", "give" -> {
                if (!p.hasPermission("starly.cashshop.cash.give")) {
                    p.sendMessage(config.getMessage("messages.command.no_permission"));
                    return false;
                }

                if (args.length != 3) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(config.getMessage("messages.command.no_player", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", args[1]
                    )));
                    return false;
                }

                Integer currentCash = null;
                try {
                    currentCash = Integer.parseInt(args[2]);
                } catch (Exception ex) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                if (currentCash < 0) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                try {
                    cashApi.addCash(target, currentCash);
                } catch (NumberFormatException e) {
                    p.sendMessage(config.getMessage("messages.command.wrong_command"));
                    return false;
                }

                p.sendMessage(config.getMessage("messages.command.give.executor", Map.of(
                        "{executor}", p.getDisplayName(),
                        "{target}", target.getDisplayName(),
                        "{cash}", currentCash.toString()
                )));
                if (target.isOnline()) {
                    target.sendMessage(config.getMessage("messages.command.give.target", Map.of(
                            "{executor}", p.getDisplayName(),
                            "{target}", target.getDisplayName(),
                            "{cash}", currentCash.toString()
                    )));
                }

                return true;
            }

            case "도움말", "help", "?" -> {
                config.getMessages("messages.command.help.cash").forEach(p::sendMessage);
                return true;
            }

            default -> {
                config.getMessages("messages.command.help.cash").forEach(p::sendMessage);
                return true;
            }
        }
    }
}
