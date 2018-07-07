package ru.hyndo.sightmenu.item;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface MenuItem {

    Predicate<IconRequest> isAvailable();

    Consumer<MenuItemClick> onClick();

    MenuIcon getIcon(IconRequest iconRequest);

}
