package ru.hyndo.sightmenu;

import org.bukkit.inventory.Inventory;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface MenuOpenProcessor {

    OpenProcessorResponse apply(MenuSession menuSession, Consumer<Inventory> inventoryConsumer);

    default OpenProcessorResponse updateItem(MenuSession menuSession, int index) {
        throw new UnsupportedOperationException("Update item is not supported in this menu open processor");
    }

}
