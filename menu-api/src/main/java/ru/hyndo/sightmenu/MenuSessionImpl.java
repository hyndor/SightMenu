package ru.hyndo.sightmenu;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.Optional;
import java.util.Map;

public class MenuSessionImpl implements MenuSession {

    private Inventory inventory;
    private MenuTemplate menuTemplate;
    private Player owner;
    private boolean isOpeningFinished;

    private Map<Integer, MenuItem> itemsByIndex;

    public MenuSessionImpl(MenuTemplate menuTemplate, MenuOpenProcessor openProcessor, Player owner) {
        this.menuTemplate = menuTemplate;
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
    public Inventory getInventory() {
        return inventory;
    }
}
