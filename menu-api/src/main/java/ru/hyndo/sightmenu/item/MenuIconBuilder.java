package ru.hyndo.sightmenu.item;

import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.ItemStackBuilder;

public class MenuIconBuilder {

    private ItemStack itemStack;
    private int index;

    public MenuIconBuilder() {}

    public MenuIconBuilder(int index) {
        this.index = index;
    }

    public MenuIconBuilder setIndex(int index) {
        this.index = index;

        return this;
    }

    public MenuIconBuilder setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;

        return this;
    }

    public MenuIconBuilder setItemStack(ItemStackBuilder builder) {
        this.itemStack = builder.build();

        return this;
    }

    public MenuIcon build() {
        return new MenuIcon(itemStack, this.index);
    }

}
