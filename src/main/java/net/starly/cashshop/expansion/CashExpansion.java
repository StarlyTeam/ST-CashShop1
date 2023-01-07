package net.starly.cashshop.expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.starly.cashshop.CashShopMain;
import net.starly.cashshop.data.PlayerCashData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CashExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "cashshop";
    }

    @Override
    public @NotNull String getAuthor() {
        return CashShopMain.getPlugin().getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return CashShopMain.getPlugin().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (identifier.equals("cash")) {
            return String.valueOf(new PlayerCashData((Player) player).getCash());
        }

        return null;
    }
}
