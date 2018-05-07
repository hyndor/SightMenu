package ru.hyndo.signmenu.example;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
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
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.io.File;

public class SightMenuExamplePlugin extends JavaPlugin implements Listener {


    private MenuApiInstance apiInstance;
    private MenuTemplate template;

    @Override
    public void onEnable() {
        apiInstance = MenuApi.prepare(this);
        saveResource("menu_loader_example.yml", false);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MenuLoaderExample(apiInstance, YamlConfiguration.loadConfiguration(new File(getDataFolder(), "menu_loader_example.yml"))), this);
        initSingleTemplate();
    }

    private void initSingleTemplate() {
        template = apiInstance
                .templateBuilder()
                .singleTemplate()
                .setName("Cool menu name")
                .setOpenProcessor(MenuOpenProcessors.standardOpen())
                .setRows(5)
                .withItem(
                        apiInstance.itemBuilder()
                                .cachedItem()
                                .withClickListener(menuItemClick -> menuItemClick.getPlayer().sendMessage("Ohh. That's a click"))
                                .setMenuIcon(new MenuIcon(new ItemStack(Material.STONE), 5))
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .perPlayerItem()
                                .withClickListener(menuItemClick -> menuItemClick.getPlayer().sendMessage("Your name: " + menuItemClick.getPlayer().getName()))
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
        }
    }
}
