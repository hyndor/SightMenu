package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractMenuItem implements MenuItem {

    private Predicate<IconRequest> available;

    private Consumer<MenuItemClick> itemClickConsumer;

    protected AbstractMenuItem(Consumer<MenuItemClick> itemClickConsumer, Predicate<IconRequest> available) {
        this.itemClickConsumer = itemClickConsumer;
        this.available = available;
    }

    @Override
    public Consumer<MenuItemClick> onClick() {
        return itemClickConsumer;
    }

    @Override
    public Predicate<IconRequest> isAvailable() {
        return this.available;
    }


}
