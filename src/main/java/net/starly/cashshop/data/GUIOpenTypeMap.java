package net.starly.cashshop.data;

import net.starly.cashshop.gui.GUIOpenType;
import net.starly.core.data.util.Tuple;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GUIOpenTypeMap {
    public static Map<Player, Tuple<GUIOpenType, String>> guiOpenTypeMap = new HashMap<>();
}
