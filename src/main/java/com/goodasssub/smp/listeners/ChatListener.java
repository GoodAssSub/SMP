package com.goodasssub.smp.listeners;

import com.goodasssub.smp.Main;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer((source, sourceDisplayName, message, viewer) -> {
            String chatMessage = Main.getInstance().getConfig().getString("chat.format");

            // TODO: fix
            if (chatMessage == null) return Component.text("(ChatFormat Error.) ")
                .append(sourceDisplayName)
                .append(Component.text(": "))
                .append(message);

            Component deserialized = MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(source, chatMessage));

            TextReplacementConfig.Builder messageReplace = TextReplacementConfig.builder();
            TextReplacementConfig.Builder playerReplace = TextReplacementConfig.builder();
            messageReplace.matchLiteral("<message>").replacement(message);
            playerReplace.matchLiteral("<player>").replacement(sourceDisplayName);
            return deserialized.replaceText(messageReplace.build()).replaceText(playerReplace.build());
        });
    }
}
