package ru.hyndo.sightmenu.paginated;

import ru.hyndo.sightmenu.InventorySwitcher;
import ru.hyndo.sightmenu.MenuFactory;
import ru.hyndo.sightmenu.MenuSession;
import ru.hyndo.sightmenu.OwnedMenu;

public interface PaginatedMenuSession extends OwnedMenu  {

    PaginatedMenuTemplate getTemplate();

    InventorySwitcher getSwitcher();

    MenuFactory getFactory();

}
