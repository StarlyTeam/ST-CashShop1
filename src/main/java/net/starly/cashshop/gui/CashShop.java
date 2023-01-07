package net.starly.cashshop.gui;

import net.starly.cashshop.CashShopMain;
import net.starly.cashshop.data.GUIOpenTypeMap;
import net.starly.core.data.Config;
import net.starly.core.data.ConfigSection;
import net.starly.core.data.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CashShop {
    private final String name;
    private final Player player;

    private final Config config;

    public CashShop(String name, Player player) {
        this.name = name;
        this.player = player;

        this.config = new Config("shop/" + name, CashShopMain.getPlugin());
        this.config.loadDefaultConfig();
    }

    public Inventory getEditor() {
        Inventory inv = Bukkit.createInventory(null, config.getInt("shop.size"), config.getString("shop.title"));

        ConfigurationSection section;
        try {
            section = config.getConfigurationSection("items");
        } catch (NoSuchMethodError e) {
            Bukkit.getLogger().info("§8[§eCashShop§8] §eST-Core §c플러그인의 버전이 너무 낮습니다. 1.4.3 이상의 버전으로 업데이트 해주세요.");
            Bukkit.getLogger().info("§8[§eCashShop§8] §eST-Core §f플러그인 다운로드 : §7https://discord.gg/TF8jqSJjCG");
            return null;
        }

        if (section.getKeys(false).size() != 0) {
            section.getKeys(false).forEach(key -> {
                ItemStack itemStack = config.getItemStack("items." + key);
                ItemMeta itemMeta = itemStack.getItemMeta();

                List<String> lore = itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>();
                List<String> newLore = new ArrayList<>();
                lore.forEach(line -> newLore.add(ChatColor.translateAlternateColorCodes('&', line)));
                newLore.addAll(List.of("", "§r§e§m====================", "", "§r§6구매 가격 》 좌클릭으로 설정", "§r§6판매 가격 》 우클릭으로 설정", "§r§6삭제 》 Shift + 좌클릭으로 삭제"));

                itemMeta.setLore(newLore);
                itemStack.setItemMeta(itemMeta);

                inv.setItem(Integer.parseInt(key), itemStack);
            });
        }

        return inv;
    }

    public void openEditor() {
        GUIOpenTypeMap.guiOpenTypeMap.put(player, new Tuple<>(GUIOpenType.EDITOR, name));
        player.openInventory(getEditor());
    }

    public Inventory getShop() {
        Inventory inv = Bukkit.createInventory(null, config.getInt("shop.size"), config.getString("shop.title"));

        ConfigurationSection section;
        try {
            section = config.getConfigurationSection("items");
        } catch (NoSuchMethodError e) {
            Bukkit.getLogger().info("§8[§eCashShop§8] §eST-Core §c플러그인의 버전이 너무 낮습니다. 1.4.3 이상의 버전으로 업데이트 해주세요.");
            Bukkit.getLogger().info("§8[§eCashShop§8] §eST-Core §f플러그인 다운로드 : §7https://discord.gg/TF8jqSJjCG");
            return null;
        }

        if (section.getKeys(false).size() != 0) {
            section.getKeys(false).forEach(key -> {
                ItemStack itemStack = config.getItemStack("items." + key);
                ConfigSection priceSection = config.getSection("items." + key + ".price");
                Integer buyPrice = priceSection.getInt("buy");
                Integer sellPrice = priceSection.getInt("sell");

                List<String> lore = CashShopMain.config.getStringList("lore.lore");
                List<String> newLore = new ArrayList<>();
                lore.forEach(line -> {
                    switch (line) {
                        case "{lore}" -> {
                            List<String> itemLore = itemStack.getItemMeta().getLore();
                            if (itemLore != null) {
                                itemLore.forEach(loreLine -> newLore.add(ChatColor.translateAlternateColorCodes('&', loreLine)));
                            }
                        }

                        case "{buy}" -> {
                            if (buyPrice == -1) {
                                newLore.add(ChatColor.translateAlternateColorCodes('&',
                                        CashShopMain.config
                                                .getString("lore.buy.cannot_buy")));
                            } else {
                                newLore.add(ChatColor.translateAlternateColorCodes('&',
                                        CashShopMain.config
                                                .getString("lore.buy.can_buy")
                                                .replace("{buy_price}", buyPrice.toString())));
                            }
                        }

                        case "{sell}" -> {
                            if (sellPrice == -1) {
                                newLore.add(ChatColor.translateAlternateColorCodes('&',
                                        CashShopMain.config
                                                .getString("lore.sell.cannot_sell")));
                            } else {
                                newLore.add(ChatColor.translateAlternateColorCodes('&',
                                        CashShopMain.config
                                                .getString("lore.sell.can_sell")
                                                .replace("{sell_price}", sellPrice.toString())));
                            }
                        }

                        default -> newLore.add(ChatColor.translateAlternateColorCodes('&', line));
                    }
                });

                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setLore(newLore);
                itemStack.setItemMeta(itemMeta);

                inv.setItem(Integer.parseInt(key), itemStack);
            });
        }

        return inv;
    }

    public void openShop() {
        GUIOpenTypeMap.guiOpenTypeMap.put(player, new Tuple<>(GUIOpenType.SHOP, name));
        player.openInventory(getShop());
    }

    public Config getConfig() {
        return config;
    }
}
