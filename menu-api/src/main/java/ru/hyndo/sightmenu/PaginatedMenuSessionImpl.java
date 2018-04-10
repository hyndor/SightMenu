package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.ArrayList;

public class PaginatedMenuSessionImpl implements PaginatedMenuSession {

    private Player owner;
    private PaginatedMenuTemplate template;
    private MenuSession currentPage;
    private int currentPageGlobalIndex;
    private MenuFactory menuFactory;

    PaginatedMenuSessionImpl(Player owner, PaginatedMenuTemplate template, MenuFactory menuFactory) {
        this.owner = owner;
        this.template = template;
        this.currentPageGlobalIndex = template.allPages().indexOf(template.mainPage());
        if(currentPageGlobalIndex == -1) {
            throw new IllegalArgumentException("Invalid template. Main page isn't among template's pages");
        }
    }

    @Override
    public PaginatedMenuTemplate getTemplate() {
        return template;
    }

    @Override
    public MenuSession switchPrevious() throws IllegalArgumentException {
        Preconditions.checkState(hasPrevious(), "Can not switch to a non-existent page");
        MenuTemplate newTemplate = template.allPages().get(--currentPageGlobalIndex);
        return menuFactory.createSingleSession(owner, newTemplate);
    }

    @Override
    public MenuSession switchNext() throws IllegalArgumentException {
        Preconditions.checkState(hasNext(), "Can not switch to a non-existent page");
        MenuTemplate newTemplate = template.allPages().get(++currentPageGlobalIndex);
        return menuFactory.createSingleSession(owner, newTemplate);
    }

    @Override
    public boolean hasPrevious() {
        int newIndex = currentPageGlobalIndex - 1;
        return template.allPages().size() > newIndex;
    }

    @Override
    public boolean hasNext() {
        int newIndex = currentPageGlobalIndex + 1;
        return template.allPages().size() > newIndex;
    }

    @Override
    public MenuSession switchToPage(int pageIndex) throws IllegalArgumentException {
        if(template.allPages().size() > pageIndex) {
            throw new IndexOutOfBoundsException("Unknown page");
        }
        this.currentPageGlobalIndex = pageIndex;
        MenuTemplate newTemplate = template.allPages().get(pageIndex);
        return menuFactory.createSingleSession(owner, newTemplate);
    }

    @Override
    public Player getOwner() {
        return owner;
    }

}
