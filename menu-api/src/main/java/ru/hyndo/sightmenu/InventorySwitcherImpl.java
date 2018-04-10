package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import ru.hyndo.sightmenu.MenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.Optional;

class InventorySwitcherImpl implements InventorySwitcher {

    private PaginatedMenuSession session;
    private int currentPageGlobalIndex;
    private MenuFactory menuFactory;

    InventorySwitcherImpl() {
    }

    @Override
    public MenuSession switchPrevious() throws IllegalArgumentException {
        Preconditions.checkState(hasPrevious(), "Can not switch to a non-existent page");
        MenuTemplate newTemplate = session.getTemplate().allPages().get(--currentPageGlobalIndex);
        return menuFactory.createSingleSession(session.getOwner(), newTemplate);
    }

    @Override
    public MenuSession switchNext() throws IllegalArgumentException {
        Preconditions.checkState(hasNext(), "Can not switch to a non-existent page");
        MenuTemplate newTemplate = session.getTemplate().allPages().get(++currentPageGlobalIndex);
        return menuFactory.createSingleSession(session.getOwner(), newTemplate);
    }

    @Override
    public boolean hasPrevious() {
        int newIndex = currentPageGlobalIndex - 1;
        return session.getTemplate().allPages().size() > newIndex;
    }

    @Override
    public boolean hasNext() {
        int newIndex = currentPageGlobalIndex + 1;
        return session.getTemplate().allPages().size() > newIndex;
    }

    @Override
    public MenuSession switchToPage(int pageIndex) throws IllegalArgumentException {
        if(session.getTemplate().allPages().size() > pageIndex) {
            throw new IndexOutOfBoundsException("Unknown page");
        }
        this.currentPageGlobalIndex = pageIndex;
        MenuTemplate newTemplate = session.getTemplate().allPages().get(pageIndex);
        return menuFactory.createSingleSession(session.getOwner(), newTemplate);
    }

    @Override
    public Optional<PaginatedMenuSession> getBoundSession() {
        return Optional.ofNullable(session);
    }

    @Override
    public void bindToSession(PaginatedMenuSession menuSession) {
        Preconditions.checkState(this.session == null, "Trying to bind session to already binned inventory switcher. Try to create new instance of inventory switcher.");
        setSession(menuSession);
    }

    void setSession(PaginatedMenuSession session) {
        PaginatedMenuTemplate template = session.getTemplate();
        this.currentPageGlobalIndex = template.allPages().indexOf(template.mainPage());
        if(currentPageGlobalIndex == -1) {
            throw new IllegalArgumentException("Invalid template. Main page isn't among template's pages");
        }
        this.session = session;
        this.menuFactory = session.getFactory();
    }



}
