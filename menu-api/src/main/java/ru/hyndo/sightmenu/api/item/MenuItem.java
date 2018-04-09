package ru.hyndo.sightmenu.api.item;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public interface MenuItem {

    int getIndex();

    ItemStack getItemStack();

}
