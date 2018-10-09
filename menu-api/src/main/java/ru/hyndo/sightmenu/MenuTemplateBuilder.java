package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import ru.hyndo.sightmenu.item.MenuItem;
import ru.hyndo.sightmenu.item.MenuItemClick;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MenuTemplateBuilder {

    MenuTemplateBuilder() {
    }

    public SingleMenuTemplateBuilder singleTemplate() {
        return new SingleMenuTemplateBuilder();
    }

    public PaginatedMenuTemplateBuilder paginatedTemplate() {
        return new PaginatedMenuTemplateBuilder();
    }

    public static class SingleMenuTemplateBuilder {
        private String name = "SightMenu";
        private List<MenuItem> items = new ArrayList<>();
        private int rows = 6;
        private MenuOpenProcessor openProcessor = MenuOpenProcessors.standardOpen();
        private Consumer<MenuTemplate.GlobalMenuClick> globalClickListener = (__) -> {};
        private Runnable onClose;

        private SingleMenuTemplateBuilder() {
        }

        public SingleMenuTemplateBuilder setName(String name) {
            Preconditions.checkNotNull(name, "Null name");
            this.name = name;
            return this;
        }

        public SingleMenuTemplateBuilder withOnClose(Runnable onClose) {
            Preconditions.checkNotNull(onClose, "onClose is null");
            this.onClose = onClose;
            return this;
        }

        public SingleMenuTemplateBuilder withGlobalClickListener(Consumer<MenuTemplate.GlobalMenuClick> listener) {
            Preconditions.checkNotNull(listener, "Listener is null");
            globalClickListener = globalClickListener.andThen(listener);
            return this;
        }

        public SingleMenuTemplateBuilder setOpenProcessor(MenuOpenProcessor openProcessor) {
            Preconditions.checkNotNull(openProcessor, "OpenProcessor is null");
            this.openProcessor = openProcessor;
            return this;
        }

        /**
         * @param menuTemplate to combine with
         * @return builder, combined with menu template. Such parameters as name, rows and open processor will be overridden from menu template. Items will be merged from current builder and menu template
         */
        public SingleMenuTemplateBuilder combineWith(MenuTemplate menuTemplate) {
            this.name = menuTemplate.getName();
            this.items.addAll(menuTemplate.getItems());
            this.rows = menuTemplate.getRows();
            this.openProcessor = menuTemplate.getOpenProcessor();
            return this;
        }

        public SingleMenuTemplateBuilder withItem(MenuItem item) {
            Preconditions.checkNotNull(item, "Null item");
            this.items.add(item);
            return this;
        }

        public SingleMenuTemplateBuilder withItems(Collection<MenuItem> items) {
            Preconditions.checkNotNull(items, "Null items");
            items.stream().filter(Objects::nonNull).forEach(item -> this.items.add(item));
            return this;
        }

        public SingleMenuTemplateBuilder setRows(int rows) {
            Preconditions.checkArgument(rows > 0 && rows <= 6, "Invalid rows amount, must be lower than 7 and bigger than 0");
            this.rows = rows;
            return this;
        }

        public MenuTemplate createMenuTemplateImpl() {
            return new MenuTemplateImpl(name, items, rows, openProcessor, globalClickListener, onClose);
        }
    }

    public static class PaginatedMenuTemplateBuilder {

        private MenuTemplate mainPage;
        private List<MenuTemplate> pages = new ArrayList<>();
        private Supplier<InventorySwitcher> inventorySwitcher = InventorySwitcherImpl::new;

        private PaginatedMenuTemplateBuilder() {
        }

        public PaginatedMenuTemplateBuilder setMainPage(MenuTemplate mainPage) {
            Preconditions.checkNotNull(mainPage, "mainPage is null");
            this.mainPage = mainPage;
            return this;
        }

        public PaginatedMenuTemplateBuilder withPage(MenuTemplate page) {
            Preconditions.checkNotNull(page, "page is null");
            this.pages.add(page);
            return this;
        }

        public PaginatedMenuTemplateBuilder setPages(List<MenuTemplate> pages) {
            Preconditions.checkNotNull(pages, "pages is null");
            this.pages = pages;
            return this;
        }

        public PaginatedMenuTemplateBuilder setInventorySwitcherFactory(Supplier<InventorySwitcher> factory) {
            Preconditions.checkNotNull(inventorySwitcher, "inventorySwitcher is null");
            this.inventorySwitcher = factory;
            return this;
        }

        public PaginatedMenuTemplate build() {
            Preconditions.checkNotNull(mainPage, "Trying to build paginated menu template with null main page");
            Preconditions.checkNotNull(pages, "Trying to build paginated menu template with null page list");
            Preconditions.checkState(!pages.isEmpty(), "Trying to build paginated menu template with empty page list");
            return new PaginatedMenuTemplateImpl(mainPage, pages, inventorySwitcher);
        }
    }

}
