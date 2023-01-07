package net.starly.cashshop;

import net.starly.cashshop.command.CashCmdTabCompleter;
import net.starly.cashshop.command.CashCommand;
import net.starly.cashshop.command.CashShopCmdTabCompleter;
import net.starly.cashshop.command.CashShopCommand;
import net.starly.cashshop.event.InventoryClickListener;
import net.starly.cashshop.event.InventoryCloseListener;
import net.starly.cashshop.event.InventoryDragListener;
import net.starly.cashshop.event.PlayerChatListener;
import net.starly.cashshop.expansion.CashExpansion;
import net.starly.core.bstats.Metrics;
import net.starly.core.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CashShopMain extends JavaPlugin {
    private static JavaPlugin plugin;
    public static Config config;

    @Override
    public void onEnable() {
        // DEPENDENCY
        if (Bukkit.getPluginManager().getPlugin("ST-Core") == null) {
            Bukkit.getLogger().warning("[" + plugin.getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            Bukkit.getLogger().warning("[" + plugin.getName() + "] 다운로드 링크 : &fhttps://discord.gg/TF8jqSJjCG");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;

        new Metrics(this, 17317);


        // CONFIG
        config = new Config("config", plugin);
        config.loadDefaultConfig();
        config.setPrefix("messages.prefix");


        // COMMAND
        Bukkit.getPluginCommand("cash").setExecutor(new CashCommand());
        Bukkit.getPluginCommand("cash").setTabCompleter(new CashCmdTabCompleter());

        Bukkit.getPluginCommand("cashshop").setExecutor(new CashShopCommand());
        Bukkit.getPluginCommand("cashshop").setTabCompleter(new CashShopCmdTabCompleter());


        // EVENT
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryDragListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), plugin);


        // EXPANSION
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getLogger().info("§8[§6캐시상점§8] §aPlaceholderAPI 플러그인이 적용되어 있습니다! PlaceholderAPI 확장을 적용합니다.");

            CashExpansion cashExpansion = new CashExpansion();
            if (cashExpansion.canRegister()) {
                cashExpansion.register();
            }
        } else {
            Bukkit.getLogger().info("§8[§6캐시상점§8] §cPlaceholderAPI 플러그인이 적용되어 있지 않습니다! PlaceholderAPI 확장을 적용하지 않습니다.");
        }
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
