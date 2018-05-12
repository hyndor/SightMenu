package ru.hyndo.sightmenu.placeholder;

import org.bukkit.entity.Player;
import ru.hyndo.sightmenu.item.MenuItemClick;

import java.util.Map;

public class DefaultPlaceholderMapper extends AbstractPlaceHolderMapper {

    @Override
    protected void populateMap(MenuItemClick click, Map<String, Object> payload, Map<String, String> values) {
        Player player = click.getPlayer();
        values.put("player", player.getName());
        values.put("health", String.valueOf((int) player.getHealth()));
    }

}
