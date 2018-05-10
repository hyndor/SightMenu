package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.hyndo.sightmenu.item.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuSessionImplTest {

    @Mock
    private MenuItem item1;
    @Mock
    private MenuItem item2;
    @Mock
    private MenuTemplate template;
    @Mock
    private Player player;
    @Mock
    private Consumer<Map<String, Object>> headerConsumer;

    private MenuSessionImpl session;

    @Before
    public void setUp() {
        MenuOpenProcessor openProcessor = (data, a) -> {
            Map<Integer, MenuItem> items = new HashMap<>();
            items.put(2, item1);
            items.put(16, item2);
            return new OpenProcessorResponse(mock(Inventory.class), items);
        };
        session = new MenuSessionImpl(template, openProcessor, player, headerConsumer);
    }

    @Test
    public void getItemByIndex() {
        assertTrue(session.getItemByIndex(2).isPresent());
        assertTrue(session.getItemByIndex(16).isPresent());
        assertEquals(session.getItemByIndex(2).get(), item1);
        assertEquals(session.getItemByIndex(16).get(), item2);
    }

    @Test
    public void close() {
        session.close();
        verify(player, times(1)).closeInventory();
    }

    @Test
    public void sendHeader() {
        Map<String, Object> data = new HashMap<>();
        session.sendHeader(data);
        verify(headerConsumer, times(1)).accept(data);
    }

}