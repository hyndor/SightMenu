package ru.hyndo.sightmenu;

import ru.hyndo.sightmenu.item.MenuItem;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface MenuTemplate {

    @Nonnull String getName();

    @Nonnull Collection<MenuItem> getItems();

    int getRows();

    @Nonnull MenuOpenProcessor getOpenProcessor();


}
