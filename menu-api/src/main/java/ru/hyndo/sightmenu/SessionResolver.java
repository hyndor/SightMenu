package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;

import java.util.Collection;
import java.util.Optional;

public interface SessionResolver {

    /**
     * @param menuTemplate template to search
     * @return all sessions with such template
     */
    Collection<MenuSession> resolveSessions( MenuTemplate menuTemplate );

    /**
     * @param player session owner
     * @return empty if inventory isn't opened
     */
    Optional<MenuSession> resolveSession( Player player );

    /**
     * @param player session owner
     * @return returns last opened paginated session.
     */
    Optional<PaginatedMenuSession> resolveLastPaginatedSession( Player player );

    /**
     * Clear all player menu sessions.
     *
     * @param player - player
     */
    void sessionInactivated( Player player );
}
