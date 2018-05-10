package ru.hyndo.sightmenu.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FunctionUtil {

    public static <A, B> Consumer<A> bindLast(BiConsumer<A, B> fn, B b) {
        return a -> fn.accept(a, b);
    }

    public static <A, B> Consumer<B> bindFirst(BiConsumer<A, B> fn, A a) {
        return b -> fn.accept(a, b);
    }

}
