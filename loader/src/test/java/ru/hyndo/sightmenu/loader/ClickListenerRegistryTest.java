package ru.hyndo.sightmenu.loader;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClickListenerRegistryTest {

    private ClickListenerRegistry registry = new ClickListenerRegistry();

    private boolean call = false;

    @Test
    public void apply() {
        registerListener();
        call = false;
        registry.apply("test").accept(null, null);
        assertTrue(call);
    }

    @Test
    public void removeListener() {
        registerListener();
        boolean test = registry.removeListener("test");
        assertTrue(test);
        assertTrue(!registry.contains("test"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getRegisteredListeners_CheckImmutability() {
        registry.registerListener("test", (event, payload) -> call = true, true);
        registry.getRegisteredListeners().put("a", null);
        System.out.println("Got value" + registry.getRegisteredListeners().get("a"));
    }

    @Test
    public void merge() {
        ClickListenerRegistry registry2 = new ClickListenerRegistry();
        registry2.registerListener("registry2", (a, b) -> {
        }, true);
        registry2.registerListener("registry3", (a, b) -> {
        }, true);
        registry.registerListener("registry", (a, b) -> {
        }, true);
        registry.merge(registry2);
        assertTrue(registry.getRegisteredListeners().containsKey("registry"));
        assertTrue(registry.getRegisteredListeners().containsKey("registry2"));
        assertTrue(registry.getRegisteredListeners().containsKey("registry3"));
    }

    @Test
    public void registerListener() {
        registry.registerListener("test", (event, payload) -> {
            call = true;
            System.out.println("setting call true");
        }, true);
        assertTrue(registry.getRegisteredListeners().containsKey("test"));
    }
}