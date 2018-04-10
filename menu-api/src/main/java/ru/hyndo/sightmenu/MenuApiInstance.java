package ru.hyndo.sightmenu;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hyndo.sightmenu.item.MenuItem;

public class MenuApiInstance {

    private MenuFactory menuFactory;
    private JavaPlugin plugin;
    private SessionResolver sessionResolver;
    private MenuTemplateBuilder menuTemplateBuilder;
    private MenuItemBuilder itemBuilder;

    MenuApiInstance(JavaPlugin plugin) {
        this.plugin = plugin;
        SessionResolverImpl sessionResolver = new SessionResolverImpl();
        MenuListener menuListener = new MenuListener(sessionResolver);
        Bukkit.getPluginManager().registerEvents(menuListener, plugin);
        this.menuFactory = new MenuFactoryImpl(sessionResolver);
        this.menuTemplateBuilder = new MenuTemplateBuilder();
        this.sessionResolver = sessionResolver;
        this.itemBuilder = new MenuItemBuilder();
    }

    public MenuTemplateBuilder templateBuilder() {
        return menuTemplateBuilder;
    }

    public SessionResolver getSessionResolver() {
        return sessionResolver;
    }

    public MenuFactory getMenuFactory() {
        return menuFactory;
    }

    public MenuItemBuilder itemBuilder() {
        return itemBuilder;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
