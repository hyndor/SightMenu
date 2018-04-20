package ru.hyndo.sightmenu;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.List;
import java.util.function.Supplier;

class PaginatedMenuTemplateImpl implements PaginatedMenuTemplate {

    private MenuTemplate mainTemplate;
    private List<MenuTemplate> pages;
    private Supplier<InventorySwitcher> inventorySwitcher;

    PaginatedMenuTemplateImpl(MenuTemplate mainTemplate, List<MenuTemplate> pages, Supplier<InventorySwitcher> inventorySwitcher) {
        this.mainTemplate = mainTemplate;
        this.pages = pages;
        this.inventorySwitcher = inventorySwitcher;
    }

    @Override
    public MenuTemplate mainPage() {
        return mainTemplate;
    }

    @Override
    public List<MenuTemplate> allPages() {
        return ImmutableList.copyOf(pages);
    }

    @Override
    public InventorySwitcher switcher() {
        return inventorySwitcher.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PaginatedMenuTemplateImpl that = (PaginatedMenuTemplateImpl) o;

        return new EqualsBuilder()
                .append(mainTemplate, that.mainTemplate)
                .append(pages, that.pages)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(mainTemplate)
                .append(pages)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PaginatedMenuTemplateImpl{" +
                "mainTemplate=" + mainTemplate +
                ", pages=" + pages +
                ", inventorySwitcher=" + inventorySwitcher +
                '}';
    }
}
