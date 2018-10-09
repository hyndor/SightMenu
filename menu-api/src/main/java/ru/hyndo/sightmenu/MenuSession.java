package ru.hyndo.sightmenu;

import org.bukkit.inventory.InventoryHolder;
import ru.hyndo.sightmenu.item.MenuItem;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface MenuSession extends OwnedMenu, InventoryHolder {

    Optional<MenuItem> getItemByIndex(int index);

    MenuTemplate getTemplate();

    boolean isOpeningFinished();

    void close();

    void sendHeader(Map<String, Object> headers);

    @Nonnull
    Consumer<Map<String, Object>> getHeaderConsumer();

    void updateItems();

    void updateItem(int index);

    void updateTemplate(MenuTemplate menuTemplate);

    void setHeaderConsumer(@Nonnull Consumer<Map<String, Object>> consumer);

    default void addHeaderConsumer(@Nonnull Consumer<Map<String, Object>> consumer) {
        setHeaderConsumer(getHeaderConsumer().andThen(consumer));
    }

}
