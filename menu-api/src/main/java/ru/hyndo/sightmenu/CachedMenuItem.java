package ru.hyndo.sightmenu;

import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.function.Consumer;

public class CachedMenuItem extends AbstractMenuItem {

    private MenuIcon menuIcon;

    CachedMenuItem(Consumer<MenuItemClick> itemClickConsumer, MenuIcon menuIcon) {
        super(itemClickConsumer);
        this.menuIcon = menuIcon;
    }

    @Override
    public MenuIcon getIcon(IconRequest iconRequest) {
        return menuIcon;
    }


}
