package ru.hyndo.sightmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.*;
import ru.hyndo.sightmenu.item.MenuIcon;
import ru.hyndo.sightmenu.paginated.PaginatedMenuSession;
import ru.hyndo.sightmenu.paginated.PaginatedMenuTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InventorySwitcherImplTest {

    MenuApiInstance apiInstance;

    @Before
    public void setUp() throws Exception {
        Server server = mock(Server.class);
        when(server.createInventory(any(), anyInt(), anyString())).thenReturn(mock(Inventory.class));
        when(server.createInventory(any(), any(), any())).thenReturn(mock(Inventory.class));
        when(server.getPluginManager()).thenReturn(mock(PluginManager.class));
        Logger logger = Logger.getLogger("Minecraft");
        when(server.getLogger()).thenReturn(logger);
        Bukkit.setServer(server);
        JavaPlugin javaPlugin = mock(JavaPlugin.class);
        apiInstance = MenuApi.prepare(javaPlugin);
    }


    @Test(expected = IllegalStateException.class)
    public void moveNext() {
        PaginatedMenuTemplate paginatedMenuTemplate = initPaginatedTemplate();
        assertEquals(paginatedMenuTemplate.allPages().size(), 7);
        Player vasya = mock(Player.class);
        PaginatedMenuSession paginatedSession = apiInstance.getMenuFactory().createPaginatedSession(vasya, paginatedMenuTemplate);
        assertNotNull(paginatedSession);
        assertNotNull(paginatedSession.getTemplate());
        assertEquals(paginatedSession.getSwitcher().currentPageIndex(), 0);
        paginatedSession.getSwitcher().switchNext();
        paginatedSession.getSwitcher().switchNext();
        paginatedSession.getSwitcher().switchNext();
        assertEquals(paginatedSession.getSwitcher().currentPageIndex(), 3);
        paginatedSession.getSwitcher().switchPrevious();
        paginatedSession.getSwitcher().switchPrevious();
        assertEquals(paginatedSession.getSwitcher().currentPageIndex(), 1);
        paginatedSession.getSwitcher().switchToPage(6);
        assertEquals(paginatedSession.getSwitcher().currentPageIndex(), 6);
        paginatedSession.getSwitcher().switchToPage(1);
        assertEquals(paginatedSession.getSwitcher().currentPageIndex(), 1);
        Optional<MenuSession> session = apiInstance.getSessionResolver().resolveSession(vasya);
        assertTrue(session.isPresent());
        paginatedSession.getSwitcher().switchPrevious();
        paginatedSession.getSwitcher().switchPrevious();
    }


    private PaginatedMenuTemplate initPaginatedTemplate() {
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

    static int count;

    private MenuTemplate createOneFromPaginatedTemplate() {
        return apiInstance
                .templateBuilder()
                .singleTemplate()
                .setName("Страница " + count++)
                .setOpenProcessor(MenuOpenProcessors.standardOpen())
                .setRows(5)
                .withItem(
                        apiInstance.itemBuilder()
                                .cachedItem()
                                .withClickListener(menuItemClick -> {
                                    menuItemClick.getPlayer().sendMessage("О следующая страница");
                                    menuItemClick.getSession().sendHeader(MenuHeaders.SWITCH_NEXT_PAGE);
                                })
                                .setMenuIcon(new MenuIcon(new ItemStack(Material.STONE), 5))
                                .build()
                )
                .withItem(
                        apiInstance.itemBuilder()
                                .perPlayerItem()
                                .withClickListener(menuItemClick -> {
                                    menuItemClick.getPlayer().sendMessage("Назад в будущее нахуй, го?");
                                    menuItemClick.getSession().sendHeader(MenuHeaders.SWITCH_PREVIOUS_PAGE);
                                })
                                .setIconRequestConsumer(iconRequest -> new MenuIcon(new ItemStack(Material.BED), 7))
                                .build()
                )
                .createMenuTemplateImpl();
    }
}