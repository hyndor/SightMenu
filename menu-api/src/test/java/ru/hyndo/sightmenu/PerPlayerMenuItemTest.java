package ru.hyndo.sightmenu;

import org.bukkit.entity.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import ru.hyndo.sightmenu.item.IconRequest;
import ru.hyndo.sightmenu.item.MenuIcon;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PerPlayerMenuItemTest {

    @Mock
    private MenuSession menuSession;
    @Mock
    private Player player;
    @Mock
    private MenuIcon icon;

    @Spy
    Function<IconRequest, MenuIcon> iconRequestConsumer = new Function<IconRequest, MenuIcon>() {
        @Override
        public MenuIcon apply(IconRequest iconRequest) {
            return icon;
        }
    };

    @Spy
    Predicate<IconRequest> availableListener = new Predicate<IconRequest>() {
        @Override
        public boolean test(IconRequest iconRequest) {
            return true;
        }
    };

    private PerPlayerMenuItem menuItem = new PerPlayerMenuItem(click -> {
    }, iconRequestConsumer, availableListener);


    @Test
    public void getIcon() {
        IconRequest request = new IconRequest(player, menuSession);
        MenuIcon icon = menuItem.getIcon(request);
        assertEquals(this.icon, icon);
    }
}