package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.Optional;

public class MenuListener implements Listener {

    private SessionResolverImpl sessionResolver;

    public MenuListener(SessionResolverImpl sessionResolver) {
        this.sessionResolver = sessionResolver;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(!(event.getClickedInventory().getHolder() instanceof MenuSessionImpl)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Optional<MenuSession> sessionOptional = sessionResolver.getSession(player);
        sessionOptional.ifPresent(menuSession -> {
            Inventory inventory = menuSession.getInventory();
            MenuTemplate template = menuSession.getTemplate();
            Optional<MenuItem> itemOptional = menuSession.getItemByIndex(event.getRawSlot());
            itemOptional.ifPresent(menuItem -> {
                if (menuItem.isAvailable().test(new IconRequest(player, menuSession)))
                    menuItem.onClick().accept(new MenuItemClick(player, menuSession, event));
            });
            template.getGlobalClickListener().accept(new MenuTemplate.GlobalMenuClick(player, menuSession, event, itemOptional.orElse(null)));
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onClose(PlayerQuitEvent event) {
        makeInactive(event.getPlayer());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        makeInactive((Player) event.getPlayer());
    }

    private void makeInactive(Player player) {
        Optional<MenuSession> sessionOptional = sessionResolver.getSession(player);
        if(sessionOptional.isPresent()) {
            MenuSession menuSession = sessionOptional.get();
            sessionResolver.onInactiveSession(menuSession);
        }
    }


}
