package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class PaginatedMenuSessionImpl implements PaginatedMenuSession, Consumer<Map<String, Object>> {

    private Player owner;
    private PaginatedMenuTemplate template;
    private MenuSession currentPage;
    private MenuFactory menuFactory;
    private InventorySwitcher switcher;

    private Map<String, BiConsumer<String, Object>> headerHandlers = new HashMap<>();

    PaginatedMenuSessionImpl(Player owner, PaginatedMenuTemplate template, MenuFactory menuFactory) {
        this.owner = owner;
        this.template = template;
        this.menuFactory = menuFactory;
        InventorySwitcher switcher = template.switcher();
        if(!switcher.getBoundSession().isPresent()) {
            switcher.bindToSession(this);
        } else {
            PaginatedMenuSession boundTo = switcher.getBoundSession().get();
            if(boundTo != this) {
                throw new IllegalArgumentException("Got menu switcher already bound to another session");
            }
        }
        this.switcher = switcher;

        headerHandlers.put(MenuHeaders.SWITCH_NEXT_PAGE_NAME, (a, b) -> {
            if(!this.switcher.hasNext()) {
                return;
            }
            MenuSession menuSession = this.switcher.switchNext();
            menuSession.addHeaderConsumer(this);
        });
        headerHandlers.put(MenuHeaders.SWITCH_PREVIOUS_PAGE_NAME, (a, b) -> {
            if(!this.switcher.hasPrevious()) {
                return;
            }
            MenuSession menuSession = this.switcher.switchPrevious();
            menuSession.addHeaderConsumer(this);
        });
        headerHandlers.put(MenuHeaders.SWITCH_TO_PAGE_NUMBER, (str, page) -> {
            if(!(page instanceof Integer)) {
                throw new IllegalArgumentException(
                        "Received invalid header SWITCH_TO_PAGE_NUMBER. Expected integer, instead got "
                                + page.getClass().getSimpleName()
                );
            }
            int pageInt = (int) page;
            if(!this.switcher.hasPage(pageInt)) {
                return;
            }
            MenuSession menuSession = this.switcher.switchToPage(pageInt);
            menuSession.addHeaderConsumer(this);
        });
        MenuSession singleSession = menuFactory.createSingleSession(owner, template.mainPage());
        singleSession.addHeaderConsumer(this);
    }

    @Override
    public PaginatedMenuTemplate getTemplate() {
        return template;
    }

    @Override
    public InventorySwitcher getSwitcher() {
        return switcher;
    }

    @Override
    public MenuFactory getFactory() {
        return menuFactory;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void accept(Map<String, Object> headers) {
        headers.forEach((str, obj) -> {
            BiConsumer<String, Object> handler = headerHandlers.get(str);
            if(handler != null) {
                handler.accept(str, obj);
            }
        });
    }
}
