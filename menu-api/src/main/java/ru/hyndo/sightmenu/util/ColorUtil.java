package ru.hyndo.sightmenu.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String color(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

}
