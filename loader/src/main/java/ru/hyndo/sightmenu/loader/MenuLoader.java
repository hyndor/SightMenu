package ru.hyndo.sightmenu.loader;

import ru.hyndo.sightmenu.MenuTemplate;

import java.util.function.Function;

public interface MenuLoader<T> extends Function<T, MenuTemplate> {
}
