package ru.hyndo.sightmenu.registry;

import java.util.Map;

public interface ListenerRegistry<I, F> {

    void registerListener(I identifier, F listener, boolean override);

    void removeListener(I identifier);

    Map<I, RegisteredListener<I, F>> getRegisteredListeners();

    void merge(ListenerRegistry<I, F> registry);

    interface RegisteredListener<I, F> {

        I getIdentifier();

        F getListener();

    }

}
