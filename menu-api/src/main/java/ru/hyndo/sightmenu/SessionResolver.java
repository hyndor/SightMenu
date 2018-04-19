package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public interface SessionResolver {

    /**
     * @param menuTemplate template to search
     * @return all sessions with such template
     */
    Collection<MenuSession> findAllByTemplate(MenuTemplate menuTemplate);

    /**
     * @param player session owner
     * @return empty if inventory isn't opened
     */
    Optional<MenuSession> getSession(Player player);

}
