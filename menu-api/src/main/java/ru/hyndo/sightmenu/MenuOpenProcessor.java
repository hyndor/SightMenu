package ru.hyndo.sightmenu;

import org.bukkit.inventory.Inventory;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface MenuOpenProcessor extends BiFunction<MenuSession, Consumer<Inventory>, OpenProcessorResponse> {



}
