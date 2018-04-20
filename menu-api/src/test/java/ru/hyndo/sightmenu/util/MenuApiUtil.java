package ru.hyndo.sightmenu.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hyndo.sightmenu.MenuApi;
import ru.hyndo.sightmenu.MenuApiInstance;
import ru.hyndo.sightmenu.MenuTemplate;
import ru.hyndo.sightmenu.item.MenuIcon;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class MenuApiUtil {

    public static void setupBukkit() {
        if (Bukkit.getServer() != null) {
            return;
        }
        Server server = mock(Server.class);
        when(server.createInventory(any(), anyInt(), anyString())).thenReturn(mock(Inventory.class));
        when(server.createInventory(any(), any(), any())).thenReturn(mock(Inventory.class));
        when(server.getPluginManager()).thenReturn(mock(PluginManager.class));
        Logger logger = Logger.getLogger("Minecraft");
        when(server.getLogger()).thenReturn(logger);
        Bukkit.setServer(server);
    }

    public static MenuApiInstance createApiInstance() {
        JavaPlugin plugin = mock(JavaPlugin.class);
        return MenuApi.prepare(plugin);
    }


    public static MenuTemplate createRandomTemplate(MenuApiInstance apiInstance) {
        return apiInstance
                .templateBuilder()
                .singleTemplate()
                .setName("Test " + ThreadLocalRandom.current().nextLong(10000))
                .withItem(
                        apiInstance.itemBuilder().cachedItem().setMenuIcon(new MenuIcon(new ItemStack(Material.STONE), 6)).build()
                )
                .createMenuTemplateImpl();
    }

}
