package ru.hyndo.signmenu.example.todelete;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.hyndo.signmenu.example.SightMenuExamplePlugin;

public class ParticleTest implements Listener {

    private SightMenuExamplePlugin plugin;

    public ParticleTest(SightMenuExamplePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("1")) {
            player.sendMessage("spawning 1");
            new BukkitRunnable() {
                @Override
                public void run() {

                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }


}
