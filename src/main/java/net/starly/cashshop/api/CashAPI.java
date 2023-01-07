package net.starly.cashshop.api;

import net.starly.cashshop.data.PlayerCashData;
import net.starly.core.util.PreCondition;
import org.bukkit.entity.Player;

public class CashAPI {
    public Integer getCash(Player player) {
        PreCondition.nonNull(player, "플레이어는 null일 수 없습니다.");

        PlayerCashData playerCashData = new PlayerCashData(player);
        return playerCashData.getCash();
    }

    public Integer addCash(Player player, Integer amount) {
        PreCondition.nonNull(player, "플레이어는 null일 수 없습니다.");
        PreCondition.nonNull(amount, "금액은 null일 수 없습니다.");

        PlayerCashData playerCashData = new PlayerCashData(player);
        playerCashData.setCash(playerCashData.getCash() + amount);

        return playerCashData.getCash();
    }

    public Integer removeCash(Player player, Integer amount) {
        PreCondition.nonNull(player, "플레이어는 null일 수 없습니다.");
        PreCondition.nonNull(amount, "금액은 null일 수 없습니다.");

        PlayerCashData playerCashData = new PlayerCashData(player);
        playerCashData.setCash(playerCashData.getCash() - amount);

        return playerCashData.getCash();
    }

    public void setCash(Player player, Integer amount) {
        PreCondition.nonNull(player, "플레이어는 null일 수 없습니다.");
        PreCondition.nonNull(amount, "금액은 null일 수 없습니다.");

        PlayerCashData playerCashData = new PlayerCashData(player);
        playerCashData.setCash(amount);
    }

    public void resetCash(Player player) {
        PreCondition.nonNull(player, "플레이어는 null일 수 없습니다.");

        PlayerCashData playerCashData = new PlayerCashData(player);
        playerCashData.setCash(0);
    }
}
