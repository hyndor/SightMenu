package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

public interface MenuFactory {

    MenuSession createSingleSession(Player player, MenuTemplate menuTemplate);

    default MenuSession replaceOrCreateCurrentSession(Player player, MenuTemplate menuTemplate) {
        throw new UnsupportedOperationException("This menu factory does not support replacing sessions");
    }

    PaginatedMenuSession createPaginatedSession(Player player, PaginatedMenuTemplate menuTemplate);

    InventorySwitcher createDefaultInventorySwitcher();
}
