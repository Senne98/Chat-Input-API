package com.senne.chatInputAPI;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.senne.chatInputAPI.chatTextAPI.events.AsyncChatListener;
import com.senne.chatInputAPI.chatTextAPI.ChatHandler;
import com.senne.chatInputAPI.chatTextAPI.events.PlayerLeaveListener;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatInputAPIMain extends JavaPlugin {

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    private static void setPlugin(JavaPlugin plugin) {
        ChatInputAPIMain.plugin = plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        //bStats
        // You can find the plugin ids of your plugins on the page https://bstats.org/what-is-my-plugin-id
        int pluginId = 25493; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        init(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Initializes the ChatInputAPI.
     * <p>
     * This method should be called in the onEnable() method of your plugin.
     *
     * @param plugin The instance of your plugin.
     * @return True if the initialization was successful, false if ChatInputAPI was already initialized.
     */
    public static boolean init(JavaPlugin plugin) {

        if (getPlugin() != null) {
            return false; // ChatInputAPI is already initialized
        }

        setPlugin(plugin);

        // Register events
        plugin.getServer().getPluginManager().registerEvents(new AsyncChatListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), plugin);

        // Register commands
        LifecycleEventManager<Plugin> manager = plugin.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            LiteralArgumentBuilder<CommandSourceStack> cancel = Commands.literal("chatinputcancel")
                    .requires(source -> {
                        if (!(source.getSender() instanceof Player)) return false;
                        return ChatHandler.hasActiveChat((Player) source.getSender());
                    })
                    .executes(context -> {
                        Player player = (Player) context.getSource().getSender();
                        ChatHandler.runCancel(player);
                        ChatHandler.removeActiveChat(player);
                        return Command.SINGLE_SUCCESS;
                    });

            commands.getDispatcher().register(cancel);
        });

        return true;
    }
}
