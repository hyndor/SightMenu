package ru.hyndo.sightmenu.item;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.inventory.ItemStack;

public class MenuIcon {

    private ItemStack itemStack;
    private int index;

    public MenuIcon(ItemStack itemStack, int index) {
        this.itemStack = itemStack;
        this.index = index;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public MenuIcon setItemStack(ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack, "itemStack is null");
        this.itemStack = itemStack;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public MenuIcon setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MenuIcon menuIcon = (MenuIcon) o;

        return new EqualsBuilder()
                .append(getIndex(), menuIcon.getIndex())
                .append(getItemStack(), menuIcon.getItemStack())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getItemStack())
                .append(getIndex())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "MenuIcon{" +
                "itemStack=" + itemStack +
                ", index=" + index +
                '}';
    }
}
