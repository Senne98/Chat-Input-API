package com.senne.chatInputAPI;

import com.senne.chatInputAPI.chatTextAPI.events.AsyncChatListener;
import com.senne.chatInputAPI.chatTextAPI.ChatHandler;
import com.senne.chatInputAPI.chatTextAPI.events.PlayerLeaveListener;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.apache.maven.artifact.repository.metadata.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class ChatInputAPIMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register events
        getServer().getPluginManager().registerEvents(new AsyncChatListener(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), plugin);

        // Register commands
        LifecycleEventManager<Plugin> manager = getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            LiteralArgumentBuilder<CommandSourceStack> cancel = Commands.literal("chatinputcancel")
                    .requires(source -> {
                        if (!(source.getSender() instanceof Player)) return false;
                        return hasActiveChat(((Player) source.getSender()).getUniqueId());
                    })
                    .executes(context -> {
                        UUID uuid = ((Player) context.getSource().getSender()).getUniqueId();
                        ChatHandler.runCancel(uuid);
                        ChatHandler.removeActiveChat(uuid);
                        return Command.SINGLE_SUCCESS;
                    });

            commands.getDispatcher().register(cancel);
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
