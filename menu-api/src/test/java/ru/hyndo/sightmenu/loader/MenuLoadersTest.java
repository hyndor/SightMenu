package ru.hyndo.sightmenu.loader;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.hyndo.sightmenu.MenuApiInstance;
import ru.hyndo.sightmenu.util.ColorUtil;
import ru.hyndo.sightmenu.util.MenuApiUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Enchantment.class})
public class MenuLoadersTest {

    @Before
    public void setUp() throws Exception {
        Server server = MenuApiUtil.setupBukkit();
    }

    @Test
    public void yamlMenuLoader() {
//        MenuApiInstance apiInstance = MenuApiUtil.createApiInstance();
//        MenuLoaders.yamlMenuLoader(apiInstance, MenuLoaders.cachedMenuItemSerializer(apiInstance, MenuLoaders.defaultItemStackSerializer()));
    }


    //
//    @Test
//    public void defaultItemStackSerializer() {
//
//    }

//    @Test
//    public void cachedMenuItemSerializer() {
//        File testFile = MenuApiUtil.getTestFile("itemstack_file_test_loader.yml");
//        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(testFile);
//        Function<ConfigurationSection, ItemStack> configurationSectionItemStackFunction = MenuLoaders.defaultItemStackSerializer();
//        ItemStack itemStack = configurationSectionItemStackFunction.apply(configuration.getConfigurationSection("itemStack"));
//        System.out.println(itemStack);
//        assertNotNull(itemStack);
//        System.out.println("getting item meta");
//        assertNotNull(itemStack.getItemMeta());
//        assertEquals("§aКрутой предмет", itemStack.getItemMeta().getDisplayName());
//        assertEquals(Arrays.asList( "§aПервая строка", "§cВторая строка"), itemStack.getItemMeta().getLore());
//        assertEquals(Sets.newHashSet(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS), itemStack.getItemMeta().getItemFlags());
//        assertEquals(Material.WOOD, itemStack.getType());
//        assertEquals(1, itemStack.getData().getData());
//        assertEquals(2, itemStack.getAmount());
//        assertTrue(itemStack.getItemMeta().isUnbreakable());
//    }


}