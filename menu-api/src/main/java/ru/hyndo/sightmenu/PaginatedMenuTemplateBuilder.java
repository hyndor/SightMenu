package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PaginatedMenuTemplateBuilder {

    private MenuTemplate mainPage;
    private List<MenuTemplate> pages = new ArrayList<>();
    private Supplier<InventorySwitcher> inventorySwitcher = InventorySwitcherImpl::new;

    PaginatedMenuTemplateBuilder() {
    }

    public PaginatedMenuTemplateBuilder setMainPage(MenuTemplate mainPage) {
        Preconditions.checkNotNull(mainPage, "mainPage is null");
        this.mainPage = mainPage;
        return this;
    }

    public PaginatedMenuTemplateBuilder withPage(MenuTemplate page) {
        Preconditions.checkNotNull(page, "page is null");
        this.pages.add(page);
        return this;
    }

    public PaginatedMenuTemplateBuilder setPages(List<MenuTemplate> pages) {
        Preconditions.checkNotNull(pages, "pages is null");
        this.pages = pages;
        return this;
    }

    public PaginatedMenuTemplateBuilder setInventorySwitcherFactory(Supplier<InventorySwitcher> factory) {
        Preconditions.checkNotNull(inventorySwitcher, "inventorySwitcher is null");
        this.inventorySwitcher = factory;
        return this;
    }

    public PaginatedMenuTemplate build() {
        Preconditions.checkNotNull(mainPage, "Trying to build paginated menu template with null main page");
        Preconditions.checkNotNull(pages, "Trying to build paginated menu template with null page list");
        Preconditions.checkState(!pages.isEmpty(), "Trying to build paginated menu template with empty page list");
        return new PaginatedMenuTemplateImpl(mainPage, pages, inventorySwitcher);
    }
}
