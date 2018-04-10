package ru.hyndo.signmenu.example;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hyndo.sightmenu.MenuApi;
import ru.hyndo.sightmenu.MenuApiInstance;
import ru.hyndo.sightmenu.MenuOpenProcessors;
import ru.hyndo.sightmenu.MenuTemplate;
import ru.hyndo.sightmenu.item.MenuIcon;

public class SightMenuExamplePlugin extends JavaPlugin implements Listener {


    private MenuApiInstance apiInstance;
    private MenuTemplate template;

    @Override
    public void onEnable() {
        apiInstance = MenuApi.prepare(this);
        template = apiInstance
                .templateBuilder()
                .singleTemplate()
                .setName("Топ меню")
                .setOpenProcessor(MenuOpenProcessors.standardOpen())
                .setRows(5)
                .withItem(
                        apiInstance.itemBuilder()
                                .cachedItem()
                                .withClickListener(menuItemClick -> menuItemClick.getPlayer().sendMessage("Епать ни встать ты кликнул"))
                                .setMenuIcon(new MenuIcon(new ItemStack(Material.STONE), 5))
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .perPlayerItem()
                                .withClickListener(menuItemClick -> menuItemClick.getPlayer().sendMessage("Твое имя " + menuItemClick.getPlayer().getName()))
                                .setIconRequestConsumer(iconRequest -> new MenuIcon(new ItemStack(Material.BED), 4))
                                .build()
                )
                .createMenuTemplateImpl();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if(message.equalsIgnoreCase("меню")) {
            apiInstance.getMenuFactory().createSingleSession(event.getPlayer(), template);
        }
    }
}
