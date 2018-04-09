package ru.hyndo.sightmenu.api;

import org.bukkit.plugin.java.JavaPlugin;
import ru.hyndo.sightmenu.api.option.MenuOptions;

public interface MenuFactory {

    default MenuSession createMenu(MenuTemplate menuTemplate) {
        return createMenu(menuTemplate, MenuOptions.empty());
    }

    MenuSession createMenu(MenuTemplate menuTemplate, MenuOptions options);

}
