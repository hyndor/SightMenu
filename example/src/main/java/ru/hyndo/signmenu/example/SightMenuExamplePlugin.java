package ru.hyndo.signmenu.example;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hyndo.sightmenu.*;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.ArrayList;
import java.util.List;

public class SightMenuExamplePlugin extends JavaPlugin implements Listener {


    private MenuApiInstance apiInstance;
    private MenuTemplate template;

    private PaginatedMenuTemplate paginatedMenuTemplate;

    private static int count = 0;

    @Override
    public void onEnable() {
        apiInstance = MenuApi.prepare(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        initPaginatedTemplate();
        initSingleTemplate();
    }

    private void initPaginatedTemplate() {
        InventorySwitcher switcher = apiInstance.getMenuFactory().createDefaultInventorySwitcher();
        List<MenuTemplate> templates = initManySingleTemplates(switcher);
        paginatedMenuTemplate = apiInstance
                .templateBuilder()
                .paginatedTemplate()
                .setMainPage(templates.get(0))
                .setInventorySwitcher(switcher)
                .setPages(templates)
                .build();
    }

    private List<MenuTemplate> initManySingleTemplates(InventorySwitcher inventorySwitcher) {
        List<MenuTemplate> templates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            templates.add(createOneFromPaginatedTemplate(inventorySwitcher));
        }
        return templates;
    }

    private MenuTemplate createOneFromPaginatedTemplate(InventorySwitcher inventorySwitcher) {
        return apiInstance
                .templateBuilder()
                .singleTemplate()
                .setName("Страница " + count++)
                .setOpenProcessor(MenuOpenProcessors.standardOpen())
                .setRows(5)
                .withItem(
                        apiInstance.itemBuilder()
                                .cachedItem()
                                .withClickListener(menuItemClick -> {
                                    menuItemClick.getPlayer().sendMessage("О следующая страница");
                                    inventorySwitcher.switchNext();
                                })
                                .setMenuIcon(new MenuIcon(new ItemStack(Material.STONE), 5))
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .perPlayerItem()
                                .withClickListener(menuItemClick -> {
                                    menuItemClick.getPlayer().sendMessage("Назад в будущее нахуй, го?");
                                    inventorySwitcher.switchPrevious();
                                })
                                .setIconRequestConsumer(iconRequest -> new MenuIcon(new ItemStack(Material.BED), 7))
                                .build()
                )
                .createMenuTemplateImpl();
    }

    private void initSingleTemplate() {
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
    }

    @EventHandler
    public void onJoin(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if(message.equalsIgnoreCase("single")) {
            apiInstance.getMenuFactory().createSingleSession(event.getPlayer(), template);
        } else if(message.equalsIgnoreCase("paginated")) {
            apiInstance.getMenuFactory().createPaginatedSession(event.getPlayer(), paginatedMenuTemplate);
        }
    }
}
