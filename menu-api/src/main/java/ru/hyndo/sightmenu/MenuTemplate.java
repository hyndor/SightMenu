package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;

public interface MenuTemplate {

    @Nonnull String getName();

    @Nonnull Collection<MenuItem> getItems();

    int getRows();

    @Nonnull MenuOpenProcessor getOpenProcessor();

    default Runnable onClose() {
        return () -> {};
    }

    default Consumer<GlobalMenuClick> getGlobalClickListener() {
        return (__) -> {};
    }

    class GlobalMenuClick extends MenuItemClick {

        private final MenuItem clicked;

        public GlobalMenuClick(Player player, MenuSession session, InventoryClickEvent event, @Nullable MenuItem clicked) {
            super(player, session, event);
            this.clicked = clicked;
        }

        @Nullable
        public MenuItem getClickedItem() {
            return clicked;
        }
    }

}
