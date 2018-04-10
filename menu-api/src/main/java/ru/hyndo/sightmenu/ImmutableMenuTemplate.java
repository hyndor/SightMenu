package ru.hyndo.sightmenu;

import com.google.common.collect.ImmutableSet;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.*;

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<MenuItem> getItems() {
        return ImmutableSet.copyOf(indexes);
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public MenuOpenProcessor getOpenProcessor() {
        return openProcessor;
    }


}
