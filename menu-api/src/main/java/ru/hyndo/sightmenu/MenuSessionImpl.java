package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.hyndo.sightmenu.item.MenuItem;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class MenuSessionImpl implements MenuSession {

    private Inventory inventory;
    private MenuTemplate menuTemplate;
    private Player owner;
    private boolean isOpeningFinished;
    private MenuOpenProcessor openProcessor;
    private Consumer<Map<String, Object>> headerConsumer;

    private Map<Integer, MenuItem> itemsByIndex;

    protected MenuSessionImpl(MenuTemplate menuTemplate, MenuOpenProcessor openProcessor, Player owner, Consumer<Map<String, Object>> headerConsumer) {
        this.menuTemplate = menuTemplate;
        this.openProcessor = openProcessor;
        this.headerConsumer = headerConsumer;
        this.owner = owner;
        OpenProcessorResponse response = openProcessor.apply(this, inventory -> {
            isOpeningFinished = true;
            this.inventory = inventory;
        });
        this.inventory = response.getInventory();
        this.itemsByIndex = response.getItemIndexes();
    }

    @Override
    public Optional<MenuItem> getItemByIndex(int index) {
        return Optional.ofNullable(itemsByIndex.get(index));
    }

    @Override
    public MenuTemplate getTemplate() {
        return menuTemplate;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public boolean isOpeningFinished() {
        return isOpeningFinished;
    }

    @Override
    public void close() {
        owner.closeInventory();
    }

    @Override
    public void sendHeader(Map<String, Object> headers) {
        headerConsumer.accept(headers);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Nonnull
    @Override
    public Consumer<Map<String, Object>> getHeaderConsumer() {
        return headerConsumer;
    }

    @Override
    public void updateItems() {
        isOpeningFinished = false;
        openProcessor.apply(this, inventory1 -> {
            this.inventory = inventory1;
            isOpeningFinished = true;
        });
    }

    @Override
    public void updateItem(int index) {
        OpenProcessorResponse response = openProcessor.updateItem(this, index);
        this.itemsByIndex.putAll(response.getItemIndexes());
    }

    @Override
    public void updateTemplate(MenuTemplate menuTemplate) {
        this.menuTemplate = menuTemplate;
        updateItems();
    }

    @Override
    public void setHeaderConsumer(@Nonnull Consumer<Map<String, Object>> consumer) {
        Preconditions.checkNotNull(consumer, "Null header consumer");
        this.headerConsumer = consumer;
    }
}
