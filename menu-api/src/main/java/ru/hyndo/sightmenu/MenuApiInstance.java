package ru.hyndo.sightmenu;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hyndo.sightmenu.item.MenuItem;

public class MenuApiInstance {

    private MenuFactory menuFactory;
    private JavaPlugin plugin;
    private SessionResolver sessionResolver;

    MenuApiInstance(JavaPlugin plugin) {
        this.plugin = plugin;
        SessionResolverImpl sessionResolver = new SessionResolverImpl();
        MenuListener menuListener = new MenuListener(sessionResolver);
        Bukkit.getPluginManager().registerEvents(menuListener, plugin);
        this.menuFactory = new MenuFactoryImpl(sessionResolver);
        this.sessionResolver = sessionResolver;
    }

    public MenuTemplateBuilder templateBuilder() {
        return new MenuTemplateBuilder();
    }

    public SessionResolver getSessionResolver() {
        return sessionResolver;
    }

    public MenuFactory getMenuFactory() {
        return menuFactory;
    }

    public MenuItemBuilder itemBuilder() {
        return new MenuItemBuilder();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

}
