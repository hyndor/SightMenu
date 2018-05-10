package ru.hyndo.sightmenu.registry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListenerRegistriesTest {

    @Mock
    private ListenerRegistry<String, String> listenerRegistry;

    @SuppressWarnings("unchecked")
    @Test
    public void copyOfListenerRegistry() {
        assertNotNull(listenerRegistry);
        Map<String, ListenerRegistry.RegisteredListener<String, String>> listeners = getListeners();
        when(listenerRegistry.getRegisteredListeners()).thenReturn(listeners);
        ListenerRegistry<String, String> copy = ListenerRegistries.copyOf(listenerRegistry, map -> {
            ListenerRegistry<String, String> registry = mock(ListenerRegistry.class);
            when(registry.getRegisteredListeners()).thenReturn(map);
            return registry;
        });
        listeners.clear();
        assertEquals(3, copy.getRegisteredListeners().size());

    }

    private Map<String, ListenerRegistry.RegisteredListener<String, String>> getListeners() {
        Map<String, ListenerRegistry.RegisteredListener<String, String>> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            map.put(String.valueOf(i), ListenerRegistries.newRegisteredListener(String.valueOf(i), String.valueOf(i)));
        }
        return map;
    }

    @Test
    public void newRegisteredListener() {
        ListenerRegistry.RegisteredListener<String, Integer> test = ListenerRegistries.newRegisteredListener("test", 2);
        assertEquals("test", test.getIdentifier());
        assertEquals(Integer.valueOf(2), test.getListener());
    }

    @Test
    public void newImmutableListener() {
        MutableClass id = new MutableClass("id");
        MutableClass listener = new MutableClass("listener");
        ListenerRegistry.RegisteredListener<MutableClass, MutableClass> registeredListener =
                ListenerRegistries.newImmutableRegisteredListener(id, listener, new ListenerRegistries.ImmutableListenerProvider<MutableClass, MutableClass>() {
                    @Override
                    public MutableClass toImmutableIdentifier(MutableClass mutableClass) {
                        return new MutableClass(mutableClass.a);
                    }

                    @Override
                    public MutableClass toImmutableListener(MutableClass mutableClass) {
                        return new MutableClass(mutableClass.a);
                    }
                });
        assertEquals("id", registeredListener.getIdentifier().a);
        assertEquals("listener", registeredListener.getListener().a);
        id.a = "changed";
        listener.a = "changed";
        assertEquals("id", registeredListener.getIdentifier().a);
        assertEquals("listener", registeredListener.getListener().a);
    }

    private static class MutableClass {

        public String a;

        public MutableClass(String a) {
            this.a = a;
        }
    }

}