package ru.hyndo.sightmenu.paginated;

import ru.hyndo.sightmenu.InventorySwitcher;
import ru.hyndo.sightmenu.MenuTemplate;

import java.util.List;
import java.util.function.Supplier;

public interface PaginatedMenuTemplate extends Iterable<MenuTemplate> {

    MenuTemplate mainPage( );

    List<MenuTemplate> allPages( );

    Supplier<InventorySwitcher> switcherSupplier( );

}
