package ru.hyndo.sightmenu;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class SessionResolverImpl implements SessionResolver {


    private Multimap<MenuTemplate, MenuSession> activeSessionsByTemplates = HashMultimap.create();
    private Map<Player, MenuSession> playerSessions = new WeakHashMap<>();
    private Map<Player, PaginatedMenuSession> paginatedSessions = new WeakHashMap<>();


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

    @Override
    public Optional<PaginatedMenuSession> getLastPaginatedSession(Player player) {
        return Optional.ofNullable(paginatedSessions.get(player));
    }

    void addNewPaginatedSession(PaginatedMenuSession session) {
        paginatedSessions.put(session.getOwner(), session);
    }

    void addNewSession(MenuSession session) {
        activeSessionsByTemplates.put(session.getTemplate(), session);
        playerSessions.put(session.getOwner(), session);
    }

    void replaceSessionTemplate(MenuSession session) {
        activeSessionsByTemplates.put(session.getTemplate(), session);
        playerSessions.put(session.getOwner(), session);
    }

    void onInactiveSession(MenuSession session) {
        activeSessionsByTemplates.remove(session.getOwner(), session);
        playerSessions.remove(session.getOwner());
        session.getTemplate().onClose().accept(session.getOwner());
    }

}
