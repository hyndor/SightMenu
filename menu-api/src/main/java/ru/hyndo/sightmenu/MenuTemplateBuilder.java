package ru.hyndo.sightmenu;

import com.google.common.collect.MultimapBuilder;

public class MenuTemplateBuilder {

    MenuTemplateBuilder() {
    }

    public SingleMenuTemplateBuilder singleTemplate() {
        return new SingleMenuTemplateBuilder();
    }

    public PaginatedMenuTemplateBuilder paginatedTemplate() {
        return new PaginatedMenuTemplateBuilder();
    }

}
