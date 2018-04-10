package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;

import java.util.Optional;

public interface InventorySwitcher {

    MenuSession switchPrevious() throws IllegalArgumentException;

    MenuSession switchNext() throws IllegalArgumentException;

    boolean hasPrevious();

    boolean hasNext();

    MenuSession switchToPage(int pageIndex) throws IllegalArgumentException;

    Optional<PaginatedMenuSession> getBoundSession();

    void bindToSession(PaginatedMenuSession menuSession);

}
