package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.List;

public class PaginatedMenuTemplateBuilder {

    private MenuTemplate mainPage;
    private List<MenuTemplate> pages;

    PaginatedMenuTemplateBuilder() {
    }

    public PaginatedMenuTemplateBuilder setPages(List<MenuTemplate> pages) {
        this.pages = pages;
        return this;
    }

    public PaginatedMenuTemplateBuilder setMainPage(MenuTemplate mainPage) {
        this.mainPage = mainPage;
        return this;
    }

    public PaginatedMenuTemplate build() {
        return new PaginatedMenuTemplateImpl(mainPage, pages);
    }
}
