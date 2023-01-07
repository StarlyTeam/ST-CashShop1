package net.starly.cashshop.data;

import net.starly.core.data.util.Tuple;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ChatInputMap {
    public static Map<Player, Tuple<String, Integer>> chatInputMap = new HashMap<>();
}
