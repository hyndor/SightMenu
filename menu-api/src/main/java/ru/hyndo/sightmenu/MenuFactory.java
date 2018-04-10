package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

public interface MenuFactory {

    MenuSession createSingleSession(Player player, MenuTemplate menuTemplate);

    PaginatedMenuSession createPaginatedSession(Player player, PaginatedMenuTemplate menuTemplate);

    InventorySwitcher createDefaultInventorySwitcher();

}
