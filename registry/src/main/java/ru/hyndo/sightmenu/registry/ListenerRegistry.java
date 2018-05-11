package ru.hyndo.sightmenu.registry;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Actual holder for registered listeners
 */
public interface ListenerRegistry<I, F> {

    /**
     * @param identifier listener id
     * @param listener   actual handler
     * @return registered listener. If listener with such name already exists and override flag is false than, it returns previously registered listener
     */
    default RegisteredListener<I, F> registerListener(I identifier, F listener) {
        return registerListener(identifier, listener, false);
    }

    /**
     * Processor will be attached to every registered listener
     *
     * @param processor handler
     */
    void addListenerPreProcessor(UnaryOperator<F> processor);

    /**
     * @param identifier listener id
     * @param listener   actual handler
     * @param override   shows if existing listener should be overridden
     * @return registered listener. If listener with such name already exists and override flag is false than, it returns previously registered listener
     */
    RegisteredListener<I, F> registerListener(I identifier, F listener, boolean override);

    /**
     * @param identifier id
     * @return true if listener exists
     */
    boolean contains(I identifier);

    /**
     * @param listener registered listener
     * @return true if listener exists
     */
    default boolean contains(RegisteredListener<I, F> listener) {
        return contains(listener.getIdentifier());
    }

    /**
     * @param identifier id
     * @return true if listener with such id existed
     */
    boolean removeListener(I identifier);

    /**
     * @param registeredListener id
     * @return true if listener with such id existed
     */
    default boolean removeListener(RegisteredListener<I, F> registeredListener) {
        return removeListener(registeredListener.getIdentifier());
    }

    /**
     * @param id of the listener
     * @return registered listener
     */
    Optional<RegisteredListener<I, F>> getRegisteredListener(I id);

    /**
     * @return unmodifiable view of all registered listeners
     */
    Map<I, RegisteredListener<I, F>> getRegisteredListeners();

    /**
     * Add all registered listeners to the current registry, making full copy of registered listeners.
     *
     * @param registry to merge
     */
    void merge(ListenerRegistry<I, F> registry);

    /**
     * Represents registered listener
     */
    interface RegisteredListener<I, F> {

        I getIdentifier();

        F getListener();

    }

}
