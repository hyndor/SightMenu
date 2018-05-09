package ru.hyndo.sightmenu.loader;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import ru.hyndo.sightmenu.item.MenuItemClick;
import ru.hyndo.sightmenu.registry.ListenerRegistries;
import ru.hyndo.sightmenu.registry.ListenerRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class ClickListenerRegistry implements BiFunction<String, Map<String, Object>, Consumer<MenuItemClick>>,
        ListenerRegistry<String, Function<Map<String, Object>, Consumer<MenuItemClick>>> {

    private static final Logger LOGGER = Logger.getLogger(ClickListenerRegistry.class.getName());
    private Map<String, RegisteredListener<String, Function<Map<String, Object>, Consumer<MenuItemClick>>>> registryMap = new ConcurrentHashMap<>();

    public ClickListenerRegistry(Map<String, RegisteredListener<String, Function<Map<String, Object>, Consumer<MenuItemClick>>>> registryMap) {
        Preconditions.checkNotNull(registryMap, "Null registryMap");
        this.registryMap.putAll(registryMap);
    }

    public ClickListenerRegistry() {
    }

    @Override
    public Consumer<MenuItemClick> apply(String name, Map<String, Object> payload) {
        return registryMap.get(name).getListener().apply(payload);
    }

    @Override
    public void removeListener(String name) {
        Preconditions.checkNotNull(name, "Null name");
        registryMap.remove(name);
    }

    @Override
    public Map<String, RegisteredListener<String, Function<Map<String, Object>, Consumer<MenuItemClick>>>> getRegisteredListeners() {
        return ImmutableMap.copyOf(registryMap);
    }

    @Override
    public void merge(ListenerRegistry<String, Function<Map<String, Object>, Consumer<MenuItemClick>>> registry) {
        registryMap.putAll(ListenerRegistries.copyOf(this, ClickListenerRegistry::new).getRegisteredListeners());
    }

    @Override
    public void registerListener(String name, Function<Map<String, Object>, Consumer<MenuItemClick>> handler, boolean override) {
        Preconditions.checkNotNull(name, "Null name");
        Preconditions.checkNotNull(handler, "Null handler");
        if (registryMap.containsKey(name) && !override) {
            LOGGER.warning(() -> String.format("Listener with name %s already exists. If you want to override, enable override flag", name));
            return;
        }
        registryMap.put(name, ListenerRegistries.newRegisteredListener(name, handler));
    }

}
