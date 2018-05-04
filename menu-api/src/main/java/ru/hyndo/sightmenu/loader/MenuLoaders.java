package ru.hyndo.sightmenu.loader;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.*;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;
import ru.hyndo.sightmenu.util.ColorUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.hyndo.sightmenu.util.ColorUtil.color;

public class MenuLoaders {

    public static MenuLoader yamlMenuLoader(MenuApiInstance apiInstance,
                                            Function<ConfigurationSection, MenuItem> itemSerializer) {
        return new SimpleYamlMenuLoader(apiInstance, itemSerializer);
    }

    public static Function<ConfigurationSection, ItemStack> defaultItemStackSerializer() {
        return DefaultItemStackSerializer.INSTANCE;
    }

    public static Function<ConfigurationSection, MenuItem> cachedMenuItemSerializer(MenuApiInstance apiInstance,
                                                                                    Function<ConfigurationSection, ItemStack> itemStackSerializer) {
        return new CachedMenuItemSerializer(apiInstance, itemStackSerializer);
    }

    private static class CachedMenuItemSerializer implements Function<ConfigurationSection, MenuItem> {

        private MenuApiInstance apiInstance;
        private Function<ConfigurationSection, ItemStack> itemStackSerializer;
//        private BiFunction<String, Map<String, Object>, Consumer<MenuItemClick>> listenerProvider;

        public CachedMenuItemSerializer(MenuApiInstance apiInstance,
                                        Function<ConfigurationSection, ItemStack> itemStackSerializer
        ) {
            this.apiInstance = apiInstance;
            this.itemStackSerializer = itemStackSerializer;
//            this.listenerProvider = listenerProvider;
        }

        @Override
        public MenuItem apply(ConfigurationSection cfg) {
            ItemStack itemStack = itemStackSerializer.apply(cfg.getConfigurationSection("itemStack"));
            MenuIcon menuIcon = new MenuIcon(itemStack, cfg.getInt("slot"));
            return apiInstance
                    .itemBuilder()
                    .cachedItem()
                    .setMenuIcon(menuIcon)
                    .build();
        }
    }

    private enum DefaultItemStackSerializer implements Function<ConfigurationSection, ItemStack> {
        INSTANCE;

        @Override
        public ItemStack apply(ConfigurationSection itemStackCfg) {
            int amount = itemStackCfg.getInt("amount", 1);
            int data = itemStackCfg.getInt("data", 0);
            String material = itemStackCfg.getString("material");
            ConfigurationSection enchantmentsCfg = itemStackCfg.getConfigurationSection("enchantments");
            Map<Enchantment, Integer> enchantments = getEnchantments(enchantmentsCfg);
            List<ItemFlag> itemFlags = getFlags(itemStackCfg.getConfigurationSection("itemFlags"));
            List<String> lore = ColorUtil.format( (lore = itemStackCfg.getStringList("lore")) == null ? new ArrayList<>() : lore);
            String displayName = itemStackCfg.getString("name");
            boolean unbreakable = itemStackCfg.getBoolean("unbreakable", false);
            if(material == null) {
                throw new IllegalArgumentException("Can not construct menuItem without material. Please specify material. Example: \"material: STONE\"");
            }
            ItemStackBuilder.ItemStackItemMetaBuilder builder = ItemStackBuilder
                    .create()
                    .setAmount(amount)
                    .setData((short) data)
                    .setMaterial(Material.getMaterial(material.toUpperCase()))
                    .setEnchantments(enchantments)
                    .withItemMeta()
                    .setItemFlags(itemFlags)
                    .setLore(lore)
                    .setName(displayName)
                    .setUnbreakable(unbreakable);
            if(displayName != null) {
                builder.setName(ColorUtil.color(displayName));
            }
            return builder.and().build();
        }

        private List<ItemFlag> getFlags(ConfigurationSection cfg) {
            return Optional.ofNullable(cfg.getStringList("flags")).map(list -> list.stream()
                    .map(ItemFlag::valueOf)
                    .collect(Collectors.toList())).orElse(new ArrayList<>());
        }

        private Map<Enchantment, Integer> getEnchantments(ConfigurationSection cfg) {
            return cfg.getKeys(false)
                    .stream()
                    .map(cfg::getConfigurationSection)
                    .collect(Collectors.toMap(
                            enchCfg -> Enchantment.getByName(enchCfg.getString("enchantment")),
                            enchCfg -> enchCfg.getInt("level")));
        }

    }

    public static class SimpleYamlMenuLoader implements MenuLoader<ConfigurationSection> {

        private MenuApiInstance apiInstance;
        private Function<ConfigurationSection, MenuItem> menuItemSerializer;

        SimpleYamlMenuLoader(MenuApiInstance apiInstance, Function<ConfigurationSection, MenuItem> menuItemSerializer) {
            this.apiInstance = apiInstance;
            this.menuItemSerializer = menuItemSerializer;
        }

        @Override
        public MenuTemplate apply(ConfigurationSection cfg) {
            ConfigurationSection meta = cfg.getConfigurationSection("meta");
            String name = color(meta.getString("name"));
            int rows = meta.getInt("rows");
            ConfigurationSection itemsCfg = cfg.getConfigurationSection("items");
            return apiInstance
                    .templateBuilder()
                    .singleTemplate()
                    .setName(name)
                    .setOpenProcessor(MenuOpenProcessors.standardOpen())
                    .setRows(rows)
                    .withItems(itemsCfg
                            .getKeys(false)
                            .stream()
                            .map(itemsCfg::getConfigurationSection)
                            .map(menuItemSerializer)
                            .collect(Collectors.toSet()))
                    .createMenuTemplateImpl();
        }

    }

}
