package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MenuItemBuilder {

    public CachedMenuItemBuilder cachedItem() {
        return new CachedMenuItemBuilder();
    }

    public PerPlayerMenuItemBuilder perPlayerItem() {
        return new PerPlayerMenuItemBuilder();
    }

    @SuppressWarnings("unchecked cast")
    public abstract static class AbstractMenuItemBuilder<T extends AbstractMenuItemBuilder<T>> {

        Predicate<IconRequest> available = iconRequest -> true;

        Consumer<MenuItemClick> onClick = (click) -> { };

        AbstractMenuItemBuilder() {
        }

        public T withAvailableListener(Predicate<IconRequest> isAvailable) {
            Preconditions.checkNotNull(isAvailable, "isAvailable is null");
            this.available = this.available.and(isAvailable);
            return (T) this;
        }

        public T withAvailableListener(Collection<Predicate<IconRequest>> isAvailableList) {
            Preconditions.checkNotNull(onClick, "onClick is null");
            isAvailableList.forEach(iterated -> this.available = this.available.and(iterated));
            return (T) this;
        }

        public T withClickListener(Consumer<MenuItemClick> onClick) {
            Preconditions.checkNotNull(onClick, "onClick is null");
            this.onClick = this.onClick.andThen(onClick);
            return (T) this;
        }

        public T withClickListener(Collection<Consumer<MenuItemClick>> onClickList) {
            Preconditions.checkNotNull(onClick, "onClick is null");
            onClickList.forEach(iterated -> this.onClick = this.onClick.andThen(iterated));
            return (T) this;
        }

        public void addToBuilder(MenuTemplateBuilder.SingleMenuTemplateBuilder builder) {
            builder.withItem(build());
        }

        public abstract MenuItem build();

    }

    public static class PerPlayerMenuItemBuilder extends AbstractMenuItemBuilder<PerPlayerMenuItemBuilder> {

        private Function<IconRequest, MenuIcon> iconRequestConsumer;

        PerPlayerMenuItemBuilder() {
        }

        public PerPlayerMenuItemBuilder setIconRequestConsumer(Function<IconRequest, MenuIcon> iconRequestConsumer) {
            Preconditions.checkNotNull(iconRequestConsumer, "iconRequestConsumer is null");
            this.iconRequestConsumer = iconRequestConsumer;
            return this;
        }

        @Override
        public PerPlayerMenuItem build() {
            return new PerPlayerMenuItem(onClick, iconRequestConsumer, available);
        }

    }


    public static class CachedMenuItemBuilder extends AbstractMenuItemBuilder<CachedMenuItemBuilder> {

        private MenuIcon menuIcon = new MenuIcon(new ItemStack(Material.AIR), 0);

        CachedMenuItemBuilder() {
        }

        public CachedMenuItemBuilder setMenuIcon(MenuIcon menuIcon) {
            Preconditions.checkNotNull(menuIcon, "menuIcon is null");
            this.menuIcon = menuIcon;
            return this;
        }

        @Override
        public CachedMenuItem build() {
            return new CachedMenuItem(onClick, menuIcon, available);
        }


    }

}
