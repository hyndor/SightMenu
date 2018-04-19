package ru.hyndo.signmenu.example;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hyndo.sightmenu.*;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

public class SightMenuExamplePlugin2 extends JavaPlugin implements Listener {


    protected PaginatedMenuTemplate paginatedMenuTemplate;
    private MenuApiInstance apiInstance;
    private MenuTemplate template;

    @Override
    public void onEnable() {
        apiInstance = MenuApi.prepare(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        paginatedMenuTemplate = new PaginatedExample(apiInstance).initPaginatedTemplate();
        initSingleTemplate();
    }


    private void initSingleTemplate() {

        MenuTemplate template = apiInstance
                .templateBuilder()
                .singleTemplate()
                .setName("Cool menu name")
                .setOpenProcessor(MenuOpenProcessors.standardOpen())
                .setRows(5)
                .withItem(
                        apiInstance.itemBuilder()
                                .perPlayerItem()
                                .withClickListener(menuItemClick -> menuItemClick.getPlayer().sendMessage("You have clicked! Congratulations"))
                                .withClickListener(menuItemClick -> menuItemClick.getPlayer().sendMessage("Yeah. You can specify as many click listeners as you want"))
                                .setIconRequestConsumer(iconRequest -> new MenuIcon(ItemStackBuilder.create()
                                        .setMaterial(Material.STONE)
                                        .addBlankLore()
                                        .addLore("Your name: " + iconRequest.getPlayer().getName())
                                        .build(), 5)
                                )
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .cachedItem()
                                .setMenuIcon(new MenuIcon(ItemStackBuilder.create()
                                        .setMaterial(Material.STONE)
                                        .build(), 6)
                                )
                                .build()
                )
                .createMenuTemplateImpl();
    }

    @EventHandler
    public void onJoin(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (message.equalsIgnoreCase("single")) {
            apiInstance.getMenuFactory().createSingleSession(event.getPlayer(), template);
        } else if (message.equalsIgnoreCase("paginated")) {
            apiInstance.getMenuFactory().createPaginatedSession(event.getPlayer(), paginatedMenuTemplate);
        }
    }
}
