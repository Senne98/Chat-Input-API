package com.senne.chatInputAPI.chatTextAPI.events;

import com.senne.chatInputAPI.chatTextAPI.ChatHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        ChatHandler.removeActiveChat(e.getPlayer());
    }
}
