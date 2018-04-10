package ru.hyndo.sightmenu.paginated;

import ru.hyndo.sightmenu.MenuTemplate;

import java.util.List;

public interface PaginatedMenuTemplate {

    /**
     * @return empty if
     */
    MenuTemplate mainPage();

    List<MenuTemplate> allPages();



}
