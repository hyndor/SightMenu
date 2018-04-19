package ru.hyndo.sightmenu.item;

import java.util.function.Consumer;

public interface MenuItem {

    Consumer<MenuItemClick> onClick();

    MenuIcon getIcon(IconRequest iconRequest);

}
