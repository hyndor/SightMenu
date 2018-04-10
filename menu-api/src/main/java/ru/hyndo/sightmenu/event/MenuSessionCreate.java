package ru.hyndo.sightmenu.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import ru.hyndo.sightmenu.MenuSession;

public class MenuSessionCreate extends Event {

    private static final HandlerList handlers = new HandlerList();

    private MenuSession menuSession;

    public MenuSessionCreate(MenuSession menuSession) {
        this.menuSession = menuSession;
    }

    public MenuSession getMenuSession() {
        return menuSession;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
