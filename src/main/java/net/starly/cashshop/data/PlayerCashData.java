package net.starly.cashshop.data;

import net.starly.cashshop.CashShopMain;
import net.starly.core.data.Config;
import net.starly.core.util.PreCondition;
import org.bukkit.entity.Player;

public class PlayerCashData {
    private final Player player;

    private final Config config;

    public PlayerCashData(Player player) {
        this.player = player;

        this.config = new Config("data/" + player.getUniqueId(), CashShopMain.getPlugin());

        if (!config.isFileExist()) {
            config.setInt("cash", 0);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Config getConfig() {
        return config;
    }

    public Integer getCash() {
        return config.getInt("cash");
    }

    public void setCash(Integer cash) {
        PreCondition.nonNull(cash, "캐시는 null일 수 없습니다.");
        config.setInt("cash", cash);
    }
}
