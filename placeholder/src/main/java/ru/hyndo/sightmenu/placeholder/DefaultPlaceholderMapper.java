package ru.hyndo.sightmenu.placeholder;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.HashMap;
import java.util.Map;

public class DefaultPlaceholderMapper extends AbstractPlaceHolderMapper {

    @Override
    protected Map<String, String> getValuesToReplace(MenuItemClick click, Map<String, Object> payload) {
        Player player = click.getPlayer();
        Map<String, String> values = new HashMap<>();
        values.put("player", player.getName());
        values.put("health", String.valueOf((int) player.getHealth()));
        return values;
    }

}
