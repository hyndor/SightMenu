package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class CachedMenuItem extends AbstractMenuItem {

    private MenuIcon menuIcon;

    CachedMenuItem(Consumer<MenuItemClick> itemClickConsumer, MenuIcon menuIcon, Predicate<IconRequest> available) {
        super(itemClickConsumer, available);
        this.menuIcon = menuIcon;
    }

    @Override
    public MenuIcon getIcon(IconRequest iconRequest) {
        return menuIcon;
    }


}
