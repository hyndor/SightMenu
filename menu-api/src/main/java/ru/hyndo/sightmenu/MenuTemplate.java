package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.item.MenuItem;

import java.util.Collection;
import java.util.Optional;

public interface MenuTemplate {

    String getName();

    Collection<MenuItem> getItems();

    int getRows();

    MenuOpenProcessor getOpenProcessor();

}
