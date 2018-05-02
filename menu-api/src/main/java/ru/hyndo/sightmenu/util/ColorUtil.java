package ru.hyndo.sightmenu.util;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColorUtil {

    public static String color(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static String[] format(String[] strings) {
        return format(Arrays.asList(strings)).toArray(new String[strings.length]);
    }

    public static List<String> format(List<String> strings) {
        return strings.stream().map(ColorUtil::color).collect(Collectors.toList());
    }
}
