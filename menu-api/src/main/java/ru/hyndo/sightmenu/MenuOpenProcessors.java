package ru.hyndo.sightmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

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
            Inventory inv;
            if(menu.getInventory() != null) {
                inv = menu.getInventory();
                for (int index = 0; index < inv.getSize(); index++) {
                    inv.setItem(index, new ItemStack(Material.AIR));
                }
            } else {
                inv = Bukkit.createInventory(menu, menu.getTemplate().getRows() * 9, color(menu.getTemplate().getName()));
            }
            for(MenuItem item : menu.getTemplate().getItems()){
                IconRequest request = new IconRequest(menu.getOwner(), menu);
                MenuIcon icon = item.getIcon(request);

                if(icon.getIndex() < 0 || icon.getIndex() >= menu.getTemplate().getRows() * 9 || !item.isAvailable().test(request))
                    continue;

                inv.setItem(icon.getIndex(), icon.getItemStack());
                indexes.put(icon.getIndex(), item);
            }

            menu.getOwner().openInventory(inv);
            listener.accept(inv);

            return new OpenProcessorResponse(inv, indexes);
        }

        @Override
        public OpenProcessorResponse updateItem(MenuSession menuSession, int index) {
            Map<Integer, MenuItem> indexes = new HashMap<>();
            Optional<MenuItem> item = menuSession.getItemByIndex(index);
            item.ifPresent(menuItem -> {
                IconRequest request = new IconRequest(menuSession.getOwner(), menuSession);
                MenuIcon icon = menuItem.getIcon(request);
                if(icon.getIndex() < 0 || icon.getIndex() >= menuSession.getTemplate().getRows() * 9 || !menuItem.isAvailable().test(request))
                    return;
                menuSession.getInventory().setItem(icon.getIndex(), icon.getItemStack());
                indexes.put(icon.getIndex(), menuItem);
            });
            return new OpenProcessorResponse(menuSession.getInventory(), indexes);
        }

        @Override
        public String toString() {
            return "MenuOpenProcessors.standardOpen()";
        }
    }


}
