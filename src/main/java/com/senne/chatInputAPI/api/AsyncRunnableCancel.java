package com.senne.chatInputAPI.api;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface AsyncRunnableCancel {
    void run(Player player);
}
