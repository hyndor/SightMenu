package ru.hyndo.sightmenu;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class SessionResolverImpl implements SessionResolver {


    private Multimap<MenuTemplate, MenuSession> activeSessionsByTemplates = HashMultimap.create();
    private Map<Player, MenuSession> playerSessions = Maps.newHashMap();

    SessionResolverImpl() {
    }

    @Override
    public Collection<MenuSession> findAllByTemplate(MenuTemplate menuTemplate) {
        return ImmutableSet.copyOf(activeSessionsByTemplates.get(menuTemplate));
    }

    @Override
    public Optional<MenuSession> getSession(Player player) {
        return Optional.ofNullable(playerSessions.get(player));
    }



    void addNewSession(MenuSession session) {
        activeSessionsByTemplates.put(session.getTemplate(), session);
        playerSessions.put(session.getOwner(), session);
    }


    void onInactiveSession(MenuSession session) {
        activeSessionsByTemplates.remove(session.getOwner(), session);
        playerSessions.remove(session.getOwner());
    }
}
