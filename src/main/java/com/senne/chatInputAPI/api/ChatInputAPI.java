package com.senne.chatInputAPI.api;

import com.senne.chatInputAPI.chatTextAPI.ChatHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class contains methods to interact with the ChatInputAPI
 */
public class ChatInputAPI {

    /**
     * Stops the input for the player without calling the onCancel method.
     * @param player The player to stop the input for.
     */
    public static void stopInput(@NotNull Player player) {
        ChatHandler.removeActiveChat(player);
    }

    /**
     * Cancels the input for the player and calls the onCancel method.
     * @param player The player to cancel the input for.
     */
    public static void cancelInput(@NotNull Player player) {
        ChatHandler.runCancel(player);
        ChatHandler.removeActiveChat(player);
    }

    /**
     * Creates a new input for the player.
     * @param p The player to create the input for.
     * @param data The data to pass to the onSend method.
     * @param onCancel The method to call when the input is cancelled.
     * @param onSend The method to call when the input is sent.
     */
    public static void newInput(@NotNull Player player, @NotNull String data, @Nullable AsyncRunnableCancel onCancel, @Nullable AsyncRunnableSend onSend) {
        ChatHandler.addActiveChat(player, data, onCancel, onSend);
    }

    /**
     * Creates a new input for the player with predefined info messages and cancel button.
     * @param player The player to create the input for.
     * @param data The data to pass to the onSend method.
     * @param onCancel The method to call when the input is cancelled.
     * @param onSend The method to call when the input is sent.
     * @param message The message to send to the player.
     * @param cancelMessage The message shown when hovering over the cancel button.
     */
    public static void newInput(@NotNull Player player, @NotNull String data, @Nullable AsyncRunnableCancel onCancel, @Nullable AsyncRunnableSend onSend, @Nullable Component message, @NotNull Component cancelMessage) {
        ChatHandler.addActiveChat(player, data, onCancel, onSend);
        if (message != null) player.sendMessage(Component.text().decoration(TextDecoration.ITALIC, false).append(message));
        player.sendMessage(Component.text("[Cancel]")
                .hoverEvent(HoverEvent.showText(Component.text().color(NamedTextColor.RED).append(cancelMessage)))
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.RED)
                .clickEvent(ClickEvent.runCommand("/chatTextAPI:chatinputcancel")));
    }

    /**
     * Creates a new input for the player with predefined info messages and cancel button.
     * @param player The player to create the input for.
     * @param data The data to pass to the onSend method.
     * @param onCancel The method to call when the input is cancelled.
     * @param onSend The method to call when the input is sent.
     * @param message The message to send to the player.
     */
    public static void newInput(@NotNull Player player, @NotNull String data, @Nullable AsyncRunnableCancel onCancel, @Nullable AsyncRunnableSend onSend, @Nullable Component message) {
        newInput(player, data, onCancel, onSend, message, Component.text("Cancel input!"));
    }
}
