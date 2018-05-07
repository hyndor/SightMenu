package ru.hyndo.signmenu.example;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.hyndo.sightmenu.MenuApiInstance;
import ru.hyndo.sightmenu.MenuSession;
import ru.hyndo.sightmenu.MenuTemplate;
import ru.hyndo.sightmenu.loader.MenuLoader;
import ru.hyndo.sightmenu.loader.MenuLoaders;

public class MenuLoaderExample implements Listener {

    private MenuApiInstance apiInstance;
    private MenuTemplate template;

    public MenuLoaderExample(MenuApiInstance apiInstance, FileConfiguration menuHolder) {
        this.apiInstance = apiInstance;
        MenuLoaders menuLoaders = MenuLoaders.create(apiInstance);
        MenuLoader<ConfigurationSection> menuLoader = menuLoaders.yamlMenuLoader();
        template = menuLoader.apply(menuHolder.getConfigurationSection("menu"));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().equalsIgnoreCase("loader")) {
            apiInstance.getMenuFactory().createSingleSession(event.getPlayer(), template);
            event.getPlayer().sendMessage("You've opened a loaded menu");
        }
    }

}
