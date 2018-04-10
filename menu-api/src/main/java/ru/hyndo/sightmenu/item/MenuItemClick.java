package ru.hyndo.sightmenu.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.hyndo.sightmenu.MenuSession;

public class MenuItemClick extends IconRequest {

    private InventoryClickEvent event;

    public MenuItemClick(Player player, MenuSession session, InventoryClickEvent event) {
        super(player, session);
        this.event = event;
    }

    public InventoryClickEvent getEvent() {
        return event;
    }

    public void setEvent(InventoryClickEvent event) {
        this.event = event;
    }

}
