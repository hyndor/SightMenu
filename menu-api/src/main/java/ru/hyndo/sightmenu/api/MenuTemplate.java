package ru.hyndo.sightmenu.api;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.api.item.MenuItem;

import java.util.Collection;
import java.util.Optional;

public interface MenuTemplate {

    /**
     * Access guaranteed to be in O(1)
     *
     * @param index item index in menu-index system
     * @return item at current index
     */

    String getName();

    Optional<MenuItem> getItem(int index);

    Collection<MenuItem> getItems();

    int getRows();

    Player getOwner();

    MenuPayloads getPayloads();

}
