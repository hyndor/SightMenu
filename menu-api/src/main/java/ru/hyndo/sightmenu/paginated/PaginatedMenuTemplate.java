package ru.hyndo.sightmenu.paginated;

import ru.hyndo.sightmenu.InventorySwitcher;
import ru.hyndo.sightmenu.MenuTemplate;

import java.util.List;

public interface PaginatedMenuTemplate {

    MenuTemplate mainPage();

    List<MenuTemplate> allPages();

    InventorySwitcher switcher();

}
