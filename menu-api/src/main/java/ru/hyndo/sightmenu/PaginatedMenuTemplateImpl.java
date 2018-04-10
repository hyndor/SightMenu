package ru.hyndo.sightmenu;

import com.google.common.collect.ImmutableList;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.List;

public class PaginatedMenuTemplateImpl implements PaginatedMenuTemplate {

    private MenuTemplate mainTemplate;
    private List<MenuTemplate> pages;

    PaginatedMenuTemplateImpl(MenuTemplate mainTemplate, List<MenuTemplate> pages) {
        this.mainTemplate = mainTemplate;
        this.pages = pages;
    }

    @Override
    public MenuTemplate mainPage() {
        return mainTemplate;
    }

    @Override
    public List<MenuTemplate> allPages() {
        return ImmutableList.copyOf(pages);
    }

}
