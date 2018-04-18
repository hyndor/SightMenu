package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PaginatedMenuSessionImpl implements PaginatedMenuSession, Consumer<Map<String, Object>> {

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
        if(!template.switcher().getBoundSession().isPresent()) {
            template.switcher().bindToSession(this);
        } else {
            PaginatedMenuSession boundTo = template.switcher().getBoundSession().get();
            if(boundTo != this) {
                throw new IllegalArgumentException("Got menu switcher already bound to another session");
            }
        }

        headerHandlers.put(MenuHeaders.SWITCH_NEXT_PAGE_NAME, (a, b) -> switcher.switchNext());
        headerHandlers.put(MenuHeaders.SWITCH_PREVIOUS_PAGE_NAME, (a, b) -> switcher.switchPrevious());
        headerHandlers.put(MenuHeaders.SWITCH_TO_PAGE_NUMBER, (str, page) -> {
            if(!(page instanceof Integer)) {
                throw new IllegalArgumentException("Received invalid header SWITCH_TO_PAGE_NUMBER. Expected integer, instead got " + page.getClass().getSimpleName());
            }
            switcher.switchToPage((Integer) page);
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
