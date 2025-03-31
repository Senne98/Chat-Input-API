package com.senne.chatInputAPI.api;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface AsyncRunnableSend {
    void run(Player player, Component input, String data);
}
