package ru.hyndo.signmenu.example;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.hyndo.sightmenu.*;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.ArrayList;
import java.util.List;

public class PaginatedExample {

    private MenuApiInstance apiInstance;

    public PaginatedExample(MenuApiInstance apiInstance) {
        this.apiInstance = apiInstance;
    }

    PaginatedMenuTemplate initPaginatedTemplate() {
        List<MenuTemplate> templates = initManySingleTemplates();
        return apiInstance
                .templateBuilder()
                .paginatedTemplate()
                .setMainPage(templates.get(0))
                .setPages(templates)
                .build();
    }

    private List<MenuTemplate> initManySingleTemplates() {
        List<MenuTemplate> templates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            templates.add(createOneFromPaginatedTemplate());
        }
        return templates;
    }

    private int count;

    private MenuTemplate createOneFromPaginatedTemplate() {
        return apiInstance
                .templateBuilder()
                .singleTemplate()
                .setName("Страница " + count++)
                .setOpenProcessor(MenuOpenProcessors.standardOpen())
                .setRows(5)
                .withItem(
                        apiInstance
                                .itemBuilder()
                                .cachedItem()
                                .withClickListener(menuItemClick -> {
                                    menuItemClick.getPlayer().sendMessage("We are going to the next page");
                                    menuItemClick.getSession().sendHeader(MenuHeaders.SWITCH_NEXT_PAGE);
                                })
                                .setMenuIcon(new MenuIcon(new ItemStack(Material.STONE), 5))
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .perPlayerItem()
                                .withClickListener(menuItemClick -> {
                                    menuItemClick.getPlayer().sendMessage("We are going to the previous page");
                                    menuItemClick.getSession().sendHeader(MenuHeaders.SWITCH_PREVIOUS_PAGE);
                                })
                                .setIconRequestConsumer(iconRequest -> new MenuIcon(new ItemStack(Material.BED), 8))
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .cachedItem()
                                .withClickListener(menuItemClick -> {
                                    menuItemClick.getPlayer().sendMessage("Switching to fifth page");
                                    menuItemClick.getSession().sendHeader(MenuHeaders.switchToPage(6));
                                })
                                .setMenuIcon(new MenuIcon(new ItemStack(Material.STONE), 6))
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .perPlayerItem()
                                .setIconRequestConsumer(iconRequest -> new MenuIcon(ItemStackBuilder.create()
                                        .setMaterial(Material.ANVIL)
                                        .addBlankLore()
                                        .addLore("Your name: " + iconRequest.getPlayer().getName()).build(), 7))
                                .build()
                )
                .createMenuTemplateImpl();
    }

}
