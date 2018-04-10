package ru.hyndo.sightmenu;

import org.bukkit.inventory.InventoryHolder;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.Optional;

public interface MenuSession extends OwnedMenu, InventoryHolder {

    Optional<MenuItem> getItemByIndex(int index);

    MenuTemplate getTemplate();

    boolean isOpeningFinished();

    void close();

}
