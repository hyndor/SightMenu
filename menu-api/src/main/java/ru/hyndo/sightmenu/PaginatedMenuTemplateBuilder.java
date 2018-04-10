package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.List;

public class PaginatedMenuTemplateBuilder {

    private MenuTemplate mainPage;
    private List<MenuTemplate> pages;
    private InventorySwitcher inventorySwitcher;

    PaginatedMenuTemplateBuilder() {
    }

    public PaginatedMenuTemplateBuilder setMainPage(MenuTemplate mainPage) {
        Preconditions.checkNotNull(mainPage, "mainPage is null");
        this.mainPage = mainPage;
        return this;
    }

    public PaginatedMenuTemplateBuilder setPages(List<MenuTemplate> pages) {
        Preconditions.checkNotNull(pages, "pages is null");
        this.pages = pages;
        return this;
    }

    public PaginatedMenuTemplateBuilder setInventorySwitcher(InventorySwitcher inventorySwitcher) {
        Preconditions.checkNotNull(inventorySwitcher, "inventorySwitcher is null");
        this.inventorySwitcher = inventorySwitcher;
        return this;
    }

    public PaginatedMenuTemplate build() {
        Preconditions.checkNotNull(mainPage, "Trying build paginated menu template with null main page");
        Preconditions.checkNotNull(pages, "Trying build paginated menu template with null page list");
        Preconditions.checkNotNull(inventorySwitcher, "Trying build paginated menu template with null inventory switcher");
        Preconditions.checkState(!pages.isEmpty(), "Trying build paginated menu template with empty page list");
        return new PaginatedMenuTemplateImpl(mainPage, pages, inventorySwitcher);
    }
}
