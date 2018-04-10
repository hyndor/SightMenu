package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SingleMenuTemplateBuilder {
    private String name = "SightMenu";
    private List<MenuItem> items = new ArrayList<>();
    private int rows = 6;
    private MenuOpenProcessor openProcessor = MenuOpenProcessors.standardOpen();

    SingleMenuTemplateBuilder() {
    }

    public SingleMenuTemplateBuilder setName(String name) {
        Preconditions.checkNotNull(name, "Null name");
        this.name = name;
        return this;
    }

    public SingleMenuTemplateBuilder setOpenProcessor(MenuOpenProcessor openProcessor) {
        Preconditions.checkNotNull(openProcessor, "OpenProcessor is null");
        this.openProcessor = openProcessor;
        return this;
    }

    public SingleMenuTemplateBuilder withItem(MenuItem item) {
        Preconditions.checkNotNull(item, "Null item");
//        this.items.put(item.getIcon().getIndex(), item);
        this.items.add(item);
        return this;
    }

    public SingleMenuTemplateBuilder withItems(Collection<MenuItem> items) {
        Preconditions.checkNotNull(items, "Null items");
        items.stream().filter(Objects::nonNull).forEach(item -> this.items.add(item));
        return this;
    }

    public SingleMenuTemplateBuilder setRows(int rows) {
        Preconditions.checkArgument(rows > 0 && rows <= 6, "Invalid rows amount, must be lower than 7 and bigger than 0");
        this.rows = rows;
        return this;
    }

    public MenuTemplate createMenuTemplateImpl() {
        return new ImmutableMenuTemplate(name, items, rows, openProcessor);
    }
}