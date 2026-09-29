package me.EHOT_2025.easyQuests;

import me.EHOT_2025.easyQuests.EasyQuests;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatInputManager implements Listener {
    private static final Map<UUID, Consumer<String>> waitingInputs = new HashMap<>();

    public ChatInputManager(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void waitForInput(Player player, Consumer<String> callback) {
        waitingInputs.put(player.getUniqueId(), callback);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (waitingInputs.containsKey(uuid)) {
            event.setCancelled(true);
            Consumer<String> callback = waitingInputs.remove(uuid);

            String message = event.getMessage();
            org.bukkit.Bukkit.getScheduler().runTask(EasyQuests.getPlugin(EasyQuests.class), () -> {
                callback.accept(message);
            });
        }
    }
}