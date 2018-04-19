package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.function.Consumer;

public abstract class AbstractMenuItem implements MenuItem {

    private Consumer<MenuItemClick> itemClickConsumer;

    protected AbstractMenuItem(Consumer<MenuItemClick> itemClickConsumer) {
        this.itemClickConsumer = itemClickConsumer;
    }

    @Override
    public Consumer<MenuItemClick> onClick() {
        return itemClickConsumer;
    }


}
