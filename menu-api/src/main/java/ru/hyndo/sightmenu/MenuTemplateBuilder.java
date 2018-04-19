package ru.hyndo.sightmenu;

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
