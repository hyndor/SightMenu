package ru.hyndo.sightmenu;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;
import ru.hyndo.sightmenu.util.MenuApiUtil;

import java.io.File;

public class YamlTest {

    @Test
    public void payLoadTest() {
        File testFile = MenuApiUtil.getTestFile("default.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(testFile);
        ConfigurationSection payLoads = cfg.getConfigurationSection("listeners.messageSender.payloads");
        System.out.println("PayLoads name " + payLoads.getName());
        payLoads.getKeys(false).forEach(key -> {
            System.out.println("Key number - " + key + ". Class value: " + payLoads.get(key).getClass().getName());
        });
    }
}
