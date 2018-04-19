package ru.hyndo.sightmenu;

import com.google.common.collect.ImmutableSet;
import ru.hyndo.sightmenu.item.MenuItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuTemplateImpl implements MenuTemplate {

    private String name;
    private List<MenuItem> indexes;
    private int rows;
    private MenuOpenProcessor openProcessor;

    MenuTemplateImpl(String name, List<MenuItem> indexes, int rows, MenuOpenProcessor openProcessor) {
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
