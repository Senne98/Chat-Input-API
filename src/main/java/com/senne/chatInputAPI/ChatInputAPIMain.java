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

import java.util.UUID;

public final class ChatInputAPIMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register events
        getServer().getPluginManager().registerEvents(new AsyncChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);

        // Register commands
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
