package ru.hyndo.sightmenu.api;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import ru.hyndo.sightmenu.api.item.MenuItem;
import ru.hyndo.sightmenu.api.util.ColorUtil;

import static ru.hyndo.sightmenu.api.util.ColorUtil.color;

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
        public void accept(MenuSession menu) {
            Inventory inv = Bukkit.createInventory(null, menu.getTemplate().getRows() * 9, color(menu.getTemplate().getName()));
            for(MenuItem item : menu.getTemplate().getItems()){
                if(item.getIndex() < 0 || item.getIndex() >= menu.getTemplate().getRows() * 9){
                    continue;
                }
                inv.setItem(item.getIndex(), item.getItemStack());
            }
            menu.getOwner().openInventory(inv);
        }

        @Override
        public String toString() {
            return "MenuOpenProcessors.directExecutor()";
        }
    }


}
