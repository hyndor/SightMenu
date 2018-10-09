package ru.hyndo.sightmenu;


import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public final class MenuFactoryImpl implements MenuFactory {

    private SessionResolverImpl sessionResolver;

    protected MenuFactoryImpl(SessionResolverImpl sessionResolver) {
        this.sessionResolver = sessionResolver;
    }

    @Override
    public MenuSession createSingleSession(Player player, MenuTemplate menuTemplate) {
        MenuSession menuSession = new NotifyingMenuSession(menuTemplate, menuTemplate.getOpenProcessor(), player, map -> {}, this);
        sessionResolver.addNewSession(menuSession);
        return menuSession;
    }

    @Override
    public MenuSession replaceOrCreateCurrentSession(Player player, MenuTemplate menuTemplate) {
        Optional<MenuSession> sessionOptional = sessionResolver.getSession(player);
        if(sessionOptional.isPresent()) {
            MenuSession menuSession = sessionOptional.get();
            menuSession.updateTemplate(menuTemplate);
            return menuSession;
        } else {
            return createSingleSession(player, menuTemplate);
        }
    }

    private static final class NotifyingMenuSession extends MenuSessionImpl {

        private final MenuFactoryImpl menuFactory;

        protected NotifyingMenuSession(MenuTemplate menuTemplate,
                                       MenuOpenProcessor openProcessor,
                                       Player owner,
                                       Consumer<Map<String, Object>> headerConsumer,
                                       MenuFactoryImpl menuFactory) {
            super(menuTemplate, openProcessor, owner, headerConsumer);
            this.menuFactory = menuFactory;
        }

        @Override
        public void updateTemplate(MenuTemplate menuTemplate) {
            super.updateTemplate(menuTemplate);
            menuFactory.sessionResolver.replaceSessionTemplate(this);
        }
    }

    @Override
    public PaginatedMenuSession createPaginatedSession(Player player, PaginatedMenuTemplate menuTemplate) {
        PaginatedMenuSessionImpl paginatedMenuSession = new PaginatedMenuSessionImpl(player, menuTemplate, this);
        sessionResolver.addNewPaginatedSession(paginatedMenuSession);
        return paginatedMenuSession;
    }

    @Override
    public InventorySwitcher createDefaultInventorySwitcher() {
        return new InventorySwitcherImpl();
    }
}
