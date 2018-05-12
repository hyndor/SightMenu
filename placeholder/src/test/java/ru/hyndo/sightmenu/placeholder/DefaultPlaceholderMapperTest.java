package ru.hyndo.sightmenu.placeholder;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.hyndo.sightmenu.MenuSession;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPlaceholderMapperTest {

    @Mock
    private Player player;
    @Mock
    private Player player2;
    @Mock
    private InventoryClickEvent event;
    @Mock
    private MenuSession menuSession;
    private MenuItemClick click;
    private MenuItemClick click2;

    private boolean called = false;

    private String mutableString;

    @Before
    public void setUp() {
        when(player.getName()).thenReturn("hyndo");
        when(player.getHealth()).thenReturn(18D);
        when(player2.getName()).thenReturn("smatavon");
        when(player2.getHealth()).thenReturn(19D);
        click = new MenuItemClick(player, menuSession, event);
        click2 = new MenuItemClick(player2, menuSession, event);
    }

    @Test
    public void getValuesToReplace() {
        DefaultPlaceholderMapper mapper = new DefaultPlaceholderMapper();
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("first", 30);
        payload.put("second", "PLayer name ${player} , player health ${health}");
        BiConsumer<MenuItemClick, Map<String, Object>> applied = mapper.apply((event, map) -> {
            called = true;
            mutableString = (String) map.get("second");
        });
        applied.accept(click, payload);
        System.out.println(payload.get("second"));
        assertEquals("PLayer name ${player} , player health ${health}", payload.get("second"));
        assertEquals("PLayer name hyndo , player health 18", mutableString);
        applied.accept(click2, payload);
        assertEquals("PLayer name smatavon , player health 19", mutableString);
        assertTrue(called);

    }
}