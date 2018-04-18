package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import ru.hyndo.sightmenu.item.MenuItem;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;

public class ImmutableMenuTemplate implements MenuTemplate {

    private String name;
    private List<MenuItem> indexes;
    private int rows;
    private MenuOpenProcessor openProcessor;

    ImmutableMenuTemplate(String name, List<MenuItem> indexes, int rows, MenuOpenProcessor openProcessor) {
        this.name = name;
        this.indexes = new ArrayList<>(indexes);
        this.rows = rows;
        this.openProcessor = openProcessor;
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




}
