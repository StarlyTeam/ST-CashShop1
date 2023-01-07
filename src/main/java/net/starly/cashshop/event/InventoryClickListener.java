package net.starly.cashshop.event;

import net.starly.cashshop.gui.CashShop;
import net.starly.cashshop.gui.GUIOpenType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static net.starly.cashshop.data.GUIOpenTypeMap.guiOpenTypeMap;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        InventoryAction action = e.getAction();

        if (!guiOpenTypeMap.containsKey(p)) return;

        if (guiOpenTypeMap.get(p).getA() == GUIOpenType.SHOP) {
            e.setCancelled(true);
        } else if (guiOpenTypeMap.get(p).getA() == GUIOpenType.EDITOR) {
            String name = guiOpenTypeMap.get(p).getB();
            CashShop cashShop = new CashShop(name, p);

            // ---------------------------------------

            if (e.getClickedInventory() == p.getInventory()) {
                if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY || action == InventoryAction.COLLECT_TO_CURSOR) {
                    e.setCancelled(true);
                }
                return;
            }

            if (e.getClickedInventory() == e.getInventory()) {
                if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY || action == InventoryAction.PICKUP_ALL || action == InventoryAction.PICKUP_HALF || action == InventoryAction.PICKUP_ONE || action == InventoryAction.PICKUP_SOME || action == InventoryAction.HOTBAR_MOVE_AND_READD || action == InventoryAction.HOTBAR_SWAP || action == InventoryAction.COLLECT_TO_CURSOR || action == InventoryAction.DROP_ALL_CURSOR || action == InventoryAction.DROP_ALL_SLOT || action == InventoryAction.DROP_ONE_CURSOR || action == InventoryAction.DROP_ONE_SLOT || action == InventoryAction.SWAP_WITH_CURSOR || action == InventoryAction.NOTHING) {
                    e.setCancelled(true);
                    return;
                }
            }

            // ---------------------------------------

            ItemStack clickedSlot = e.getCursor();

            ItemMeta itemMeta = clickedSlot.getItemMeta();
            List<String> lore = clickedSlot.getItemMeta().hasLore() ? clickedSlot.getItemMeta().getLore() : new ArrayList<>();
            List<String> newLore = new ArrayList<>();

            if (lore != null) {
                newLore.addAll(lore);
            }
            if (!lore.containsAll(List.of("", "§r§e§m====================", "", "§r§6구매 가격 》 좌클릭으로 설정", "§r§6판매 가격 》 우클릭으로 설정", "§r§6삭제 》 Shift + 좌클릭으로 삭제"))) {
                newLore.addAll(List.of("", "§r§e§m====================", "", "§r§6구매 가격 》 좌클릭으로 설정", "§r§6판매 가격 》 우클릭으로 설정", "§r§6삭제 》 Shift + 좌클릭으로 삭제"));
            }

            itemMeta.setLore(newLore);
            clickedSlot.setItemMeta(itemMeta);

            e.setCurrentItem(clickedSlot);
            e.setCursor(null);

            // ---------------------------------------

            p.closeInventory();
            cashShop.openEditor();
        }
    }
}
