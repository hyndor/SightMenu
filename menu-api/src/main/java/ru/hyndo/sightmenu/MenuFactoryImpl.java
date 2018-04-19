package ru.hyndo.sightmenu;


import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

public final class MenuFactoryImpl implements MenuFactory {

    private SessionResolverImpl sessionResolver;

    MenuFactoryImpl(SessionResolverImpl sessionResolver) {
        this.sessionResolver = sessionResolver;
    }

    @Override
    public MenuSession createSingleSession(Player player, MenuTemplate menuTemplate) {
        MenuSession menuSession = new MenuSessionImpl(menuTemplate, menuTemplate.getOpenProcessor(), player, map -> {});
        sessionResolver.addNewSession(menuSession);
        return menuSession;
    }

    @Override
    public PaginatedMenuSession createPaginatedSession(Player player, PaginatedMenuTemplate menuTemplate) {
        return new PaginatedMenuSessionImpl(player, menuTemplate, this);
    }

    public InventorySwitcher createDefaultInventorySwitcher() {
        return new InventorySwitcherImpl();
    }
}
