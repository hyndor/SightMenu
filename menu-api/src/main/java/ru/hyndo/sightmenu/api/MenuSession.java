package ru.hyndo.sightmenu.api;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.api.item.MenuItem;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MenuSession {

    MenuTemplate getTemplate();

    Player getOwner();

    boolean isOpened();

    void close();

}
