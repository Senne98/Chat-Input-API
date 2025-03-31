package com.senne.chatInputAPI.chatTextAPI;

import com.senne.chatInputAPI.api.AsyncRunnableCancel;
import com.senne.chatInputAPI.api.AsyncRunnableSend;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ChatHandler {

    private static HashMap<UUID, ChatData> activeChats = new HashMap<>();

    public static void addActiveChat(Player player, String data, AsyncRunnableCancel onCancel, AsyncRunnableSend onSend) {
        activeChats.put(player.getUniqueId(), new ChatData(data, onCancel, onSend));
    }

    public static void removeActiveChat(Player player) {
        activeChats.remove(player.getUniqueId());
    }

    public static boolean hasActiveChat(Player player) {
        return activeChats.containsKey(player.getUniqueId());
    }

    public static void runCancel(Player player) {
        activeChats.get(player.getUniqueId()).runOnCancel(player);
    }

    public static void runSend(Player player, Component input) {
        activeChats.get(player.getUniqueId()).runOnSend(player, input);
    }
}

class ChatData {
    private String data;
    private AsyncRunnableCancel onCancel;
    private AsyncRunnableSend onSend;

    public ChatData(String data, AsyncRunnableCancel onCancel, AsyncRunnableSend onSend) {
        this.data = data;
        this.onCancel = onCancel;
        this.onSend = onSend;
    }

    public void runOnCancel(Player player) {
        if (onCancel != null) onCancel.run(player);
    }

    public void runOnSend(Player player, Component input) {
        if (onSend != null) onSend.run(player, input, data);
    }
}
