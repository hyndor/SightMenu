package ru.hyndo.sightmenu.paginated;

import ru.hyndo.sightmenu.MenuSession;
import ru.hyndo.sightmenu.OwnedMenu;

public interface PaginatedMenuSession extends OwnedMenu  {

    PaginatedMenuTemplate getTemplate();

    MenuSession switchPrevious() throws IllegalArgumentException;

    MenuSession switchNext() throws IllegalArgumentException;

    boolean hasPrevious();

    boolean hasNext();

    MenuSession switchToPage(int pageIndex) throws IllegalArgumentException;

}
