package ru.hyndo.sightmenu.placeholder;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;
import ru.hyndo.sightmenu.item.MenuItemClick;
import ru.hyndo.sightmenu.util.ColorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

public abstract class AbstractPlaceHolderMapper implements UnaryOperator<BiConsumer<MenuItemClick, Map<String, Object>>> {

    protected abstract Map<String, String> getValuesToReplace(MenuItemClick click, Map<String, Object> payload);

    @Override
    public BiConsumer<MenuItemClick, Map<String, Object>> apply(BiConsumer<MenuItemClick, Map<String, Object>> consumer) {
        return (itemClick, payload) -> {
            if (itemClick == null || payload == null) {
                return;
            }
            StrSubstitutor strSubstitutor = new StrSubstitutor(StrLookup.mapLookup(getValuesToReplace(itemClick, payload)));
            Map<String, Object> cached = new HashMap<>();
            new HashMap<>(payload).forEach((str, obj) -> {
                if (obj instanceof String) {
                    cached.put(str, ColorUtil.color(strSubstitutor.replace((String) obj)));
                } else {
                    cached.put(str, obj);
                }
            });
            consumer.accept(itemClick, cached);
        };
    }
}
