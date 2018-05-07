package ru.hyndo.sightmenu.util;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.hyndo.sightmenu.MenuApi;
import ru.hyndo.sightmenu.MenuApiInstance;
import ru.hyndo.sightmenu.MenuTemplate;
import ru.hyndo.sightmenu.item.MenuIcon;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class MenuApiUtil {

    private static Multimap<ItemMeta, Pair<Enchantment, Integer>> enchantmentMultimap = MultimapBuilder.hashKeys().hashSetValues().build();

    public static Server setupBukkit() {
        Server server = mock(Server.class);
        when(server.createInventory(any(), anyInt(), anyString())).thenReturn(mock(Inventory.class));
        when(server.createInventory(any(), any(), any())).thenReturn(mock(Inventory.class));
        when(server.getPluginManager()).thenReturn(mock(PluginManager.class));
        Logger logger = Logger.getLogger("Minecraft");
        when(server.getLogger()).thenReturn(logger);
        ItemFactory itemFactory = mock(ItemFactory.class);
        when(itemFactory.getItemMeta(anyObject())).thenAnswer((Answer<ItemMeta>) invocationOnMock -> new SimpleItemMeta());
        when(itemFactory.asMetaFor(anyObject(), any(ItemStack.class)))
                .thenAnswer((Answer<ItemMeta>) invocationOnMock -> (ItemMeta) invocationOnMock.getArguments()[0]);
        when(itemFactory.asMetaFor(anyObject(), any(Material.class)))
                .thenAnswer((Answer<ItemMeta>) invocationOnMock -> (ItemMeta) invocationOnMock.getArguments()[0]);
        when(itemFactory.isApplicable(anyObject(), any(ItemStack.class)))
                .thenReturn(true);
        when(itemFactory.isApplicable(anyObject(), any(Material.class)))
                .thenReturn(true);
        when(server.getItemFactory()).thenReturn(itemFactory);
        try {
            Field serverField = Bukkit.class.getDeclaredField("server");
            serverField.setAccessible(true);
            serverField.set(null, server);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return server;
    }

    public static File getTestFile(String name) {
        ClassLoader classLoader = MenuApiUtil.class.getClassLoader();
        return new File(classLoader.getResource(name).getFile());
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
