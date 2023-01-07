package net.starly.cashshop.event;

import net.starly.cashshop.CashShopMain;
import net.starly.cashshop.gui.GUIOpenType;
import net.starly.core.data.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.starly.cashshop.data.GUIOpenTypeMap.guiOpenTypeMap;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (!guiOpenTypeMap.containsKey(p)) return;
        if (guiOpenTypeMap.get(p).getA() == GUIOpenType.SHOP) return;
        String name = guiOpenTypeMap.get(p).getB();
        Inventory inv = e.getInventory();

        Config config = new Config("shop/" + name, CashShopMain.getPlugin());
        for (int i = 0; i < inv.getContents().length; i++) {
            if (inv.getItem(i) == null) {
                config.setObject("items." + i, null);
                continue;
            }

            ItemStack itemStack = inv.getItem(i);
            ItemMeta itemMeta = itemStack.getItemMeta();

            List<String> lore = itemMeta.getLore();
            List<String> newLore = new ArrayList<>();
            if (lore != null) {
                for (Object o : Arrays.copyOfRange(lore.toArray(), 0, lore.size() - 6)) {
                    newLore.add((String) o);
                }
            }

            itemMeta.setLore(newLore);
            itemStack.setItemMeta(itemMeta);

            config.setItemStack("items." + i, itemStack);
            config.setInt("items." + i + ".price.buy", 0);
            config.setInt("items." + i + ".price.sell", 0);
        }

        guiOpenTypeMap.remove(p);
    }
}
