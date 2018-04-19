package ru.hyndo.sightmenu;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.Map;

import static ru.hyndo.sightmenu.util.ColorUtil.color;

public class MenuOpenProcessors {


    /**
     * @return menu opener, which shows every item immediately
     */
    public static MenuOpenProcessor standardOpen() {
        return StandardMenuOpenProcessor.INSTANCE;
    }


    private enum StandardMenuOpenProcessor implements MenuOpenProcessor {
        INSTANCE;

        @Override
        public OpenProcessorResponse apply(MenuSession menu, Consumer<Inventory> listener) {
            Map<Integer, MenuItem> indexes = new HashMap<>();
            Inventory inv = Bukkit.createInventory(menu, menu.getTemplate().getRows() * 9, color(menu.getTemplate().getName()));
            for(MenuItem item : menu.getTemplate().getItems()){
                IconRequest request = new IconRequest(menu.getOwner(), menu);
                MenuIcon icon = item.getIcon(request);
                if(icon.getIndex() < 0 || icon.getIndex() >= menu.getTemplate().getRows() * 9){
                    continue;
                }
                inv.setItem(icon.getIndex(), icon.getItemStack());
                indexes.put(icon.getIndex(), item);
            }
            menu.getOwner().openInventory(inv);
            listener.accept(inv);
            return new OpenProcessorResponse(inv, indexes);
        }

        @Override
        public String toString() {
            return "MenuOpenProcessors.directExecutor()";
        }
    }


}
