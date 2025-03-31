package com.senne.chatInputAPI.chatTextAPI.events;

import com.senne.chatInputAPI.chatTextAPI.ChatHandler;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {

    @EventHandler
    public void onChatInput(AsyncChatEvent e) {

        Player p = e.getPlayer();

        if (!ChatHandler.hasActiveChat(p) || !ChatHandler.hasActiveChat(p)) return;
        e.setCancelled(true);

        ChatHandler.runSend(e.getPlayer(), e.message());
        ChatHandler.removeActiveChat(p);
    }
}
