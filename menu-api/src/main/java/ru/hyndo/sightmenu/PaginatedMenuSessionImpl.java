package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

public class PaginatedMenuSessionImpl implements PaginatedMenuSession {

    private Player owner;
    private PaginatedMenuTemplate template;
    private MenuSession currentPage;
    private MenuFactory menuFactory;
    private InventorySwitcher switcher;

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
        menuFactory.createSingleSession(owner, template.mainPage());
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

}
