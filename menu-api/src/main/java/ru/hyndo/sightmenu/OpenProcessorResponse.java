package ru.hyndo.sightmenu;

import org.bukkit.inventory.Inventory;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.Map;

public class OpenProcessorResponse {

    private Inventory inventory;
    private Map<Integer, MenuItem> itemIndexes;

    public OpenProcessorResponse(Inventory inventory, Map<Integer, MenuItem> itemIndexes) {
        this.inventory = inventory;
        this.itemIndexes = itemIndexes;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Integer, MenuItem> getItemIndexes() {
        return itemIndexes;
    }
}
