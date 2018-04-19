package ru.hyndo.sightmenu.item;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.MenuSession;

public class IconRequest {

    private Player player;
    private MenuSession session;

    public IconRequest(Player player, MenuSession session) {
        this.player = player;
        this.session = session;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MenuSession getSession() {
        return session;
    }

    public void setSession(MenuSession session) {
        this.session = session;
    }

}
