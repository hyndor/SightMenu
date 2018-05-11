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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPlaceholderMapperTest {

    @Mock
    private Player player;
    @Mock
    private InventoryClickEvent event;
    @Mock
    private MenuSession menuSession;
    private MenuItemClick click;

    @Before
    public void setUp() {
        when(player.getName()).thenReturn("hyndo");
        when(player.getHealth()).thenReturn(18D);
        click = new MenuItemClick(player, menuSession, event);
    }

    @Test
    public void getValuesToReplace() {
        DefaultPlaceholderMapper mapper = new DefaultPlaceholderMapper();
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("first", 30);
        payload.put("second", "PLayer name ${player} , player health ${health}");
        BiConsumer<MenuItemClick, Map<String, Object>> applied = mapper.apply((event, map) -> {
        });
        applied.accept(click, payload);
        System.out.println(payload.get("second"));
        assertEquals("PLayer name hyndo , player health 18.0", payload.get("second"));
    }
}