package ru.hyndo.sightmenu.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class AbstractListenerRegistry<I, F> implements ListenerRegistry<I, F> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractListenerRegistry.class.getName());

    protected Map<I, RegisteredListener<I, F>> registryMap = new HashMap<>();

    protected AbstractListenerRegistry(Map<I, RegisteredListener<I, F>> registryMap) {
        this.registryMap = registryMap;
    }

    protected AbstractListenerRegistry() {
    }

    @Override
    public boolean contains(I identifier) {
        return registryMap.containsKey(identifier);
    }

    @Override
    public boolean removeListener(I identifier) {
        Preconditions.checkNotNull(identifier, "Null identifier");
        return registryMap.remove(identifier) != null;
    }

    @Override
    public Optional<RegisteredListener<I, F>> getRegisteredListener(I id) {
        return Optional.ofNullable(registryMap.get(id));
    }

    @Override
    public Map<I, RegisteredListener<I, F>> getRegisteredListeners() {
        return ImmutableMap.copyOf(registryMap);
    }


}
