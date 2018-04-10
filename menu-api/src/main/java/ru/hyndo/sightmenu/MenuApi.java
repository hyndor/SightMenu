package ru.hyndo.sightmenu;

import org.bukkit.plugin.java.JavaPlugin;

public class MenuApi {

    public static MenuApiInstance prepare(JavaPlugin plugin) {
        return new MenuApiInstance(plugin);
    }

}
