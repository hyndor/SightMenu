package ru.hyndo.sightmenu.item;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface MenuItem {

    Consumer<MenuItemClick> onClick();

    MenuIcon getIcon(IconRequest iconRequest);

}
