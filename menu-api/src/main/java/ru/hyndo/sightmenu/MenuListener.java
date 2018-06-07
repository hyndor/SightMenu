package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import javax.annotation.Nonnull;
import java.util.Optional;

public class MenuListener implements Listener {

    private SessionResolverImpl sessionResolver;

    MenuListener( @Nonnull SessionResolverImpl sessionResolver ) {
        this.sessionResolver = sessionResolver;
    }

    @EventHandler
    public void onInventoryClick( InventoryClickEvent event ) {
        if ( event.getClickedInventory() == null || !( event.getClickedInventory().getHolder() instanceof MenuSession ) ) {
            return;
        }
        Player player = ( Player ) event.getWhoClicked();
        sessionResolver.resolveSession( player ).ifPresent( session -> {
            session.getItemByIndex( event.getRawSlot() ).ifPresent( menuItem -> {
                menuItem.onClick().accept( new MenuItemClick( player, session, event ) );
            } );
            event.setCancelled( true );
        } );
    }

    @EventHandler
    public void onClose( PlayerQuitEvent event ) {
        sessionResolver.sessionInactivated( event.getPlayer() );
    }

    @EventHandler
    public void onClose( InventoryCloseEvent event ) {
        sessionResolver.sessionInactivated( ( Player ) event.getPlayer() );
    }


}
