package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import com.google.common.collect.MultimapBuilder;
import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.function.Consumer;
import java.util.function.Function;

public class MenuItemBuilder {

    public CachedMenuItemBuilder cachedItem() {
        return new CachedMenuItemBuilder();
    }

    public PerPlayerMenuItemBuilder perPlayerItem() {
        return new PerPlayerMenuItemBuilder();
    }

    public abstract static class AbstractMenuItemBuilder {


        Consumer<MenuItemClick> onClick = (click) -> { };

        AbstractMenuItemBuilder() {
        }

        public AbstractMenuItemBuilder withClickListener(Consumer<MenuItemClick> onClick) {
            Preconditions.checkNotNull(onClick, "onClick is null");
            this.onClick = this.onClick.andThen(onClick);
            return this;
        }

    }

    public static class PerPlayerMenuItemBuilder extends AbstractMenuItemBuilder {

        private Function<IconRequest, MenuIcon> iconRequestConsumer;

        PerPlayerMenuItemBuilder() {
        }

        public PerPlayerMenuItemBuilder setIconRequestConsumer(Function<IconRequest, MenuIcon> iconRequestConsumer) {
            Preconditions.checkNotNull(iconRequestConsumer, "iconRequestConsumer is null");
            this.iconRequestConsumer = iconRequestConsumer;
            return this;
        }

        @Override
        public PerPlayerMenuItemBuilder withClickListener(Consumer<MenuItemClick> onClick) {
            return (PerPlayerMenuItemBuilder) super.withClickListener(onClick);
        }

        public PerPlayerMenuItem build() {
            return new PerPlayerMenuItem(onClick, iconRequestConsumer);
        }

    }


    public static class CachedMenuItemBuilder extends AbstractMenuItemBuilder {

        private MenuIcon menuIcon;

        CachedMenuItemBuilder() {
        }

        public CachedMenuItemBuilder setMenuIcon(MenuIcon menuIcon) {
            Preconditions.checkNotNull(menuIcon, "menuIcon is null");
            this.menuIcon = menuIcon;
            return this;
        }

        @Override
        public CachedMenuItemBuilder withClickListener(Consumer<MenuItemClick> onClick) {
            return (CachedMenuItemBuilder) super.withClickListener(onClick);
        }

        public CachedMenuItem build() {
            return new CachedMenuItem(onClick, menuIcon);
        }


    }

}
