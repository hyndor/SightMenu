package ru.hyndo.sightmenu;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.function.Consumer;

public class MenuHeaders {

    static final String SWITCH_NEXT_PAGE_NAME = "NEXT_PAGE";
    static final String SWITCH_TO_PAGE_NUMBER = "SWITCH_TO_PAGE_NUMBER";
    static final String SWITCH_PREVIOUS_PAGE_NAME = "PREVIOUS_PAGE";

    public static final Map<String, Object> SWITCH_NEXT_PAGE = ImmutableMap.<String, Object>builder()
            .put(SWITCH_NEXT_PAGE_NAME, new byte[0]).build();

    public static final Map<String, Object> SWITCH_PREVIOUS_PAGE = ImmutableMap.<String, Object>builder()
            .put(SWITCH_PREVIOUS_PAGE_NAME, new byte[0]).build();


    public static Map<String, Object> switchToPage(int page) {
        return ImmutableMap.<String, Object>builder()
                .put(SWITCH_TO_PAGE_NUMBER, new byte[0]).build();
    }

}
