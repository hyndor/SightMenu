package ru.hyndo.sightmenu.loader;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.ItemStackBuilder;
import ru.hyndo.sightmenu.MenuApiInstance;
import ru.hyndo.sightmenu.MenuOpenProcessors;
import ru.hyndo.sightmenu.MenuTemplate;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;
import ru.hyndo.sightmenu.registry.ListenerRegistry;
import ru.hyndo.sightmenu.registry.ListenerRegistry.RegisteredListener;
import ru.hyndo.sightmenu.util.ColorUtil;
import ru.hyndo.sightmenu.util.FunctionUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MenuLoaders {

    private static final Logger LOGGER = Logger.getLogger(MenuLoaders.class.getName());

    private MenuApiInstance apiInstance;

    private MenuLoaders(MenuApiInstance apiInstance) {
        this.apiInstance = apiInstance;
    }

    public static MenuLoaders create(MenuApiInstance apiInstance) {
        return new MenuLoaders(apiInstance);
    }

    public MenuLoader<ConfigurationSection> yamlMenuLoader(Function<ConfigurationSection, MenuItem> itemSerializer) {
        return new SimpleYamlMenuLoader(apiInstance, itemSerializer);
    }

    public Function<ConfigurationSection, ItemStack> defaultItemStackSerializer() {
        return DefaultItemStackSerializer.INSTANCE;
    }

    public Function<ConfigurationSection, MenuItem> perPlayerMenuItemSerializer() {
        return perPlayerMenuItemSerializer(defaultItemStackSerializer(), ClickListenerRegistry.STANDARD_PREDEFINED_REGISTRY);
    }

    public Function<ConfigurationSection, MenuItem> perPlayerMenuItemSerializer(Function<ConfigurationSection, ItemStack> itemStackSerializer,
                                                                                ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> registry) {
        return new PerPlayerMenuItemSerializer(apiInstance, itemStackSerializer, registry);
    }

    public Function<ConfigurationSection, MenuItem> cachedMenuItemSerializer() {
        return cachedMenuItemSerializer(defaultItemStackSerializer());
    }

    public Function<ConfigurationSection, MenuItem> cachedMenuItemSerializer(Function<ConfigurationSection, ItemStack> itemStackSerializer) {
        return new CachedMenuItemSerializer(apiInstance, itemStackSerializer);
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
            List<String> lore = ColorUtil.format((lore = itemStackCfg.getStringList("lore")) == null ? new ArrayList<>() : lore);
            String displayName = itemStackCfg.getString("name");
            boolean unbreakable = itemStackCfg.getBoolean("unbreakable", false);
            if (material == null) {
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
            if (displayName != null) {
                builder.setName(ColorUtil.color(displayName));
            }
            return builder.and().build();
        }

        private List<ItemFlag> getFlags(ConfigurationSection cfg) {
            return Optional.ofNullable(cfg).map(a -> a.getStringList("flags")).map(list -> list.stream()
                    .map(String::toUpperCase)
                    .map(ItemFlag::valueOf)
                    .collect(Collectors.toList())).orElse(new ArrayList<>());
        }

        private Map<Enchantment, Integer> getEnchantments(ConfigurationSection cfg) {
            if (cfg == null) {
                return new HashMap<>();
            }
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            cfg.getKeys(false)
                    .stream()
                    .map(cfg::getConfigurationSection)
                    .forEach(enchCfg -> enchantments.put(Enchantment.getByName(enchCfg.getString("enchantment")), enchCfg.getInt("level")));
            return enchantments;
        }

    }

    private static class SimpleYamlMenuLoader implements MenuLoader<ConfigurationSection> {

        private MenuApiInstance apiInstance;
        private Function<ConfigurationSection, MenuItem> menuItemSerializer;

        SimpleYamlMenuLoader(MenuApiInstance apiInstance, Function<ConfigurationSection, MenuItem> menuItemSerializer) {
            this.apiInstance = apiInstance;
            this.menuItemSerializer = menuItemSerializer;
        }

        @Override
        public MenuTemplate apply(ConfigurationSection cfg) {
            ConfigurationSection meta = cfg.getConfigurationSection("meta");
            String name = ColorUtil.color(meta.getString("name"));
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

    private static class CachedMenuItemSerializer extends AbstractMenuItemSerializer {

        public CachedMenuItemSerializer(MenuApiInstance apiInstance,
                                        Function<ConfigurationSection, ItemStack> itemStackSerializer) {
            super(apiInstance, itemStackSerializer);
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

    private abstract static class AbstractMenuItemSerializer implements Function<ConfigurationSection, MenuItem> {
        MenuApiInstance apiInstance;
        Function<ConfigurationSection, ItemStack> itemStackSerializer;

        AbstractMenuItemSerializer(MenuApiInstance apiInstance,
                                   Function<ConfigurationSection, ItemStack> itemStackSerializer) {
            this.apiInstance = apiInstance;
            this.itemStackSerializer = itemStackSerializer;
        }
    }

    private static class PerPlayerMenuItemSerializer extends AbstractMenuItemSerializer {

        private ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> registry;

        public PerPlayerMenuItemSerializer(MenuApiInstance apiInstance,
                                           Function<ConfigurationSection, ItemStack> itemStackSerializer,
                                           ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> registry) {
            super(apiInstance, itemStackSerializer);
            this.registry = registry;
        }

        @Override
        public MenuItem apply(ConfigurationSection cfg) {
            ItemStack itemStack = itemStackSerializer.apply(cfg.getConfigurationSection("itemStack"));
            MenuIcon menuIcon = new MenuIcon(itemStack, cfg.getInt("slot"));
            return apiInstance
                    .itemBuilder()
                    .perPlayerItem()
                    .withClickListener(collectClickListeners(cfg.getConfigurationSection("listeners")))
                    .setIconRequestConsumer(iconRequest -> menuIcon)
                    .build();
        }

        private Consumer<MenuItemClick> collectClickListeners(ConfigurationSection cfg) {
            Consumer<MenuItemClick> consumer = a -> {
            };
            Map<String, RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>>> registeredListeners = registry.getRegisteredListeners();
            for (String name : cfg.getKeys(false)) {
                if (registeredListeners.get(name.toLowerCase()) == null) {
                    LOGGER.warning(() -> String.format("Unknown listener %s. Pls check the name spell.", name));
                    continue;
                }
                ConfigurationSection listenerCfg = cfg.getConfigurationSection(name);
                BiConsumer<MenuItemClick, Map<String, Object>> listener = registeredListeners.get(name.toLowerCase()).getListener();
                Map<String, Object> payload = collectPayload(listenerCfg.getConfigurationSection("payload"));
                consumer = consumer.andThen(FunctionUtil.bindLast(listener, payload));
            }
            return consumer;
        }

        private Map<String, Object> collectPayload(ConfigurationSection cfg) {
            Map<String, Object> payload = new HashMap<>();
            cfg.getKeys(false).forEach(key -> payload.put(key, cfg.get(key)));
            return payload;
        }

    }


}
