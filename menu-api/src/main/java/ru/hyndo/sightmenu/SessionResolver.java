package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public interface SessionResolver {

    Collection<MenuSession> findAllByTemplate(MenuTemplate menuTemplate);

    Optional<MenuSession> getSession(Player player);

}
