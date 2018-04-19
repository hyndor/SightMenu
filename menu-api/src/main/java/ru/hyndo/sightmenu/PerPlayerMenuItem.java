package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.function.Consumer;
import java.util.function.Function;

public class PerPlayerMenuItem extends AbstractMenuItem {

    private Function<IconRequest, MenuIcon> iconRequestConsumer;

    PerPlayerMenuItem(Consumer<MenuItemClick> itemClickConsumer, Function<IconRequest, MenuIcon> iconRequestConsumer) {
        super(itemClickConsumer);
        this.iconRequestConsumer = iconRequestConsumer;
    }

    @Override
    public MenuIcon getIcon(IconRequest iconRequest) {
        return iconRequestConsumer.apply(iconRequest);
    }
}
