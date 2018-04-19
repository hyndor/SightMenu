package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.item.MenuItem;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface MenuTemplate {

    @Nonnull String getName();

    @Nonnull Collection<MenuItem> getItems();

    int getRows();

    @Nonnull MenuOpenProcessor getOpenProcessor();


}
