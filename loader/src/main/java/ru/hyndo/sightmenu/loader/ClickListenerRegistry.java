package ru.hyndo.sightmenu.loader;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.item.MenuItemClick;
import ru.hyndo.sightmenu.registry.ListenerRegistries;
import ru.hyndo.sightmenu.registry.ListenerRegistry;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

public class ClickListenerRegistry implements Function<String, BiConsumer<MenuItemClick, Map<String, Object>>>,
        ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> {

    private static final Logger LOGGER = Logger.getLogger(ClickListenerRegistry.class.getName());
    public static final ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> STANDARD_PREDEFINED_REGISTRY;

    static {
        ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> registry = new ClickListenerRegistry();
        registry.registerListener("messagesender", (event, payload) -> {
            Player player = event.getPlayer();
            String msg = (String) payload.get("message");
            Preconditions.checkNotNull(msg, "You've to specify message in the payload section. Example \"message: \"Some really cool message\"\"");
            player.sendMessage(msg);
        });
        registry.registerListener("commandexecutor", (event, payload) -> {
            Player player = event.getPlayer();
            String executor = (String) payload.get("executor");
            String command = (String) payload.get("command");
            Preconditions.checkNotNull(command, "You've to specify executable command in the payload section. Example \"command: \"broadcast 123\"\"");
            Preconditions.checkNotNull(executor, "You've to specify executor of the command in the payload section. Example \"executor: \"player\"\"");
            if (executor.equalsIgnoreCase("console")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } else if (executor.equalsIgnoreCase("player")) {
                Bukkit.dispatchCommand(player, command);
            } else {
                throw new IllegalArgumentException(String.format("Unknown executor type %s. Expected: console or player", executor));
            }
        });
        STANDARD_PREDEFINED_REGISTRY = ListenerRegistries.newImmutableRegistry(registry);
    }

    private Set<UnaryOperator<BiConsumer<MenuItemClick, Map<String, Object>>>> preProcessors = new HashSet<>();

    private Map<String, RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>>> registryMap = new HashMap<>();

    public ClickListenerRegistry(Map<String, RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>>> registryMap) {
        Preconditions.checkNotNull(registryMap, "Null registryMap");
        this.registryMap.putAll(registryMap);
    }

    public ClickListenerRegistry() {
    }

    @Override
    public BiConsumer<MenuItemClick, Map<String, Object>> apply(String name) {
        return registryMap.get(name).getListener();
    }

    @Override
    public boolean removeListener(String name) {
        Preconditions.checkNotNull(name, "Null name");
        return registryMap.remove(name) != null;
    }

    @Override
    public Optional<RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>>> getRegisteredListener(String id) {
        return Optional.ofNullable(registryMap.get(id));
    }

    @Override
    public Map<String, RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>>> getRegisteredListeners() {
        return ImmutableMap.copyOf(registryMap);
    }

    @Override
    public void merge(ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> registry) {
        ListenerRegistry<String, BiConsumer<MenuItemClick, Map<String, Object>>> copy = ListenerRegistries.copyOf(registry, ClickListenerRegistry::new);
        registryMap.putAll(copy.getRegisteredListeners());
        reInitMap();
    }


    @Override
    public void addListenerPreProcessor(UnaryOperator<BiConsumer<MenuItemClick, Map<String, Object>>> processor) {
        Preconditions.checkNotNull(processor, "Null processor");
        preProcessors.add(processor);
        if (preProcessors.size() != 1) {
            reInitMap();
        }
    }

    @Override
    public RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>> registerListener(
            String name, BiConsumer<MenuItemClick, Map<String, Object>> handler, boolean override) {

        Preconditions.checkNotNull(name, "Null name");
        Preconditions.checkNotNull(handler, "Null handler");
        RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>> previous = registryMap.get(name);
        if (previous != null && !override) {
            LOGGER.warning(() -> String.format("Listener with name %s already exists. If you want to override it, enable override flag", name));
            return previous;
        }
        handler = processHandler(handler);
        RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>> listener = ListenerRegistries.newRegisteredListener(name, handler);
        registryMap.put(name, listener);
        return listener;
    }

    private void reInitMap() {
        Map<String, RegisteredListener<String, BiConsumer<MenuItemClick, Map<String, Object>>>> newMap = new HashMap<>();
        registryMap.forEach((name, registeredListener) -> newMap.put(name, ListenerRegistries.newRegisteredListener(name, processHandler(registeredListener.getListener()))));
        this.registryMap = newMap;
    }

    private BiConsumer<MenuItemClick, Map<String, Object>> processHandler(BiConsumer<MenuItemClick, Map<String, Object>> handler) {
        for (UnaryOperator<BiConsumer<MenuItemClick, Map<String, Object>>> preProcessor : preProcessors) {
            handler = preProcessor.apply(handler);
        }
        return handler;
    }

    @Override
    public boolean contains(String identifier) {
        return registryMap.containsKey(identifier);
    }

}
