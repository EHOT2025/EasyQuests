package me.EHOT_2025.easyQuests;

import me.EHOT_2025.easyQuests.commands.Commands;
import me.EHOT_2025.easyQuests.database.DatabaseManager;
import me.EHOT_2025.easyQuests.listeners.MenuListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
public final class EasyQuests extends JavaPlugin {

    private static EasyQuests instance;
    private DatabaseManager databaseManager;
    private static de.eisi05.npc.api.NpcApi npcApiInstance;
    private ChatInputManager chatInputManager;


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        databaseManager = new DatabaseManager();
        databaseManager.connect();

        getCommand("easyquests").setExecutor(new Commands());
        getCommand("easyquests").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getLogger().info("EasyQuests has been enabled!");
        de.eisi05.npc.api.NpcApi.createInstance(this, new de.eisi05.npc.api.objects.NpcConfig()
                .debug(false)
                .autoUpdate(false)
        );
        npcApiInstance = de.eisi05.npc.api.NpcApi.createInstance(this, new de.eisi05.npc.api.objects.NpcConfig()
                .debug(false)
                .autoUpdate(false)
        );
        this.chatInputManager = new ChatInputManager(this);
    }

    public static de.eisi05.npc.api.NpcApi getNpcApi() {
        return npcApiInstance;
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
        de.eisi05.npc.api.NpcApi.disable();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static EasyQuests getInstance() {
        return instance;
    }

    public static String getPrefix() {
        String rawPrefix = instance.getConfig().getString("prefix", "&8[&aEasyQuests&8]&r ");
        return ChatColor.translateAlternateColorCodes('&', rawPrefix);
    }

    public ChatInputManager getChatInputManager() {
        return chatInputManager;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS quests_npcs (" +
                "uuid TEXT PRIMARY KEY," +
                "name TEXT);";
    }

    public void saveNpc(java.util.UUID uuid, String name) {
        String sql = "INSERT OR REPLACE INTO quests_npcs (uuid, name) VALUES (?, ?);";
    }
}
