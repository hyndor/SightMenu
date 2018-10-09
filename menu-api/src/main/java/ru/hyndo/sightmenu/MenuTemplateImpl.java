package ru.hyndo.sightmenu;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.item.MenuItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

class MenuTemplateImpl implements MenuTemplate {

    private String name;
    private List<MenuItem> indexes;
    private int rows;
    private MenuOpenProcessor openProcessor;
    private Consumer<GlobalMenuClick> globalClickListener;
    private Consumer<Player> onClose;

    MenuTemplateImpl(String name, List<MenuItem> indexes, int rows,
                     MenuOpenProcessor openProcessor, Consumer<GlobalMenuClick> globalClickListener,
                     Consumer<Player> onClose) {
        this.name = name;
        this.indexes = new ArrayList<>(indexes);
        this.rows = rows;
        this.openProcessor = openProcessor;
        this.globalClickListener = globalClickListener;
        this.onClose = onClose;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public Collection<MenuItem> getItems() {
        return ImmutableSet.copyOf(indexes);
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Nonnull
    @Override
    public MenuOpenProcessor getOpenProcessor() {
        return openProcessor;
    }

    @Override
    public Consumer<Player> onClose() {
        return onClose;
    }

    @Override
    public Consumer<GlobalMenuClick> getGlobalClickListener() {
        return globalClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTemplateImpl that = (MenuTemplateImpl) o;
        return rows == that.rows &&
                Objects.equals(name, that.name) &&
                Objects.equals(indexes, that.indexes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, indexes, rows);
    }
}
