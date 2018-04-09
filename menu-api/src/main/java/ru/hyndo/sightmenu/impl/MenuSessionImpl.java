package ru.hyndo.sightmenu.impl;

import ru.hyndo.sightmenu.api.MenuSession;
import ru.hyndo.sightmenu.api.MenuTemplate;

public class MenuSessionImpl implements MenuSession {
    @Override
    public MenuTemplate getTemplate() {
        return null;
    }

    @Override
    public boolean isOpened() {
        return false;
    }

    @Override
    public void close() {

    }
}
