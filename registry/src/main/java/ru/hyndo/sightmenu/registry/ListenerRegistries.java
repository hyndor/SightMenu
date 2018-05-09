package ru.hyndo.sightmenu.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ListenerRegistries {

    public static <I, F> ListenerRegistry<I, F> copyOf(ListenerRegistry<I, F> registry,
                                                       Function<Map<I, ListenerRegistry.RegisteredListener<I, F>>, ListenerRegistry<I, F>> registryFactory) {
        Map<I, ListenerRegistry.RegisteredListener<I, F>> registeredListeners = registry.getRegisteredListeners();
        Map<I, ListenerRegistry.RegisteredListener<I, F>> copy = new HashMap<>();
        registeredListeners.forEach((name, register) -> copy.put(name, new SimpleRegisteredListener<>(register.getIdentifier(), register.getListener())));
        return registryFactory.apply(copy);

    }

    public static <I, F> ListenerRegistry.RegisteredListener<I, F> newImmutableListener(I identifier, F listener,
                                                                                        ImmutableListenerProvider<I, F> immutableListenerProvider) {
        return new ImmutableRegisteredListener<>(new SimpleRegisteredListener<>(identifier, listener), immutableListenerProvider);
    }

    public static <I, F> ListenerRegistry.RegisteredListener<I, F> newImmutableListener(ListenerRegistry.RegisteredListener<I, F> listener,
                                                                                        ImmutableListenerProvider<I, F> immutableListenerProvider) {
        return new ImmutableRegisteredListener<>(listener, immutableListenerProvider);
    }

    public static <I, F> ListenerRegistry.RegisteredListener<I, F> newRegisteredListener(I id, F listener) {
        return new SimpleRegisteredListener<>(id, listener);
    }

    public interface ImmutableListenerProvider<I, F> {

        I toImmutableIdentifier(I i);

        F toImmutableListener(F f);

    }

    private static class ImmutableListenerRegistry<I, F> implements ListenerRegistry<I, F> {

        private ListenerRegistry<I, F> registry;

        public ImmutableListenerRegistry(ListenerRegistry<I, F> registry) {
            Preconditions.checkNotNull(registry);
            this.registry = registry;
        }

        @Override
        public void registerListener(I identifier, F listener, boolean override) {
            throw new UnsupportedOperationException("Can'not modify immutable Listener Registry");
        }

        @Override
        public void removeListener(I identifier) {
            throw new UnsupportedOperationException("Can'not modify unmodifiable Listener Registry");
        }

        @Override
        public Map<I, RegisteredListener<I, F>> getRegisteredListeners() {
            return ImmutableMap.copyOf(registry.getRegisteredListeners());
        }

        @Override
        public void merge(ListenerRegistry<I, F> registry) {
            throw new UnsupportedOperationException("Can'not modify unmodifiable Listener Registry");
        }
    }

    private static class ImmutableRegisteredListener<I, F> implements ListenerRegistry.RegisteredListener<I, F> {

        private ListenerRegistry.RegisteredListener<I, F> registeredListener;
        private ImmutableListenerProvider<I, F> immutableListenerProvider;

        public ImmutableRegisteredListener(ListenerRegistry.RegisteredListener<I, F> registeredListener, ImmutableListenerProvider<I, F> immutableListenerProvider) {
            this.registeredListener = registeredListener;
            this.immutableListenerProvider = immutableListenerProvider;
        }

        @Override
        public I getIdentifier() {
            return immutableListenerProvider.toImmutableIdentifier(registeredListener.getIdentifier());
        }

        @Override
        public F getListener() {
            return immutableListenerProvider.toImmutableListener(registeredListener.getListener());
        }
    }

    private static class SimpleRegisteredListener<I, F> implements ListenerRegistry.RegisteredListener<I, F> {

        private I identifier;
        private F getListener;

        private SimpleRegisteredListener(I identifier, F getListener) {
            this.identifier = identifier;
            this.getListener = getListener;
        }

        @Override
        public I getIdentifier() {
            return identifier;
        }

        @Override
        public F getListener() {
            return getListener;
        }
    }

}
