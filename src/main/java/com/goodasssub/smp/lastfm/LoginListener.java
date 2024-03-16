package com.goodasssub.smp.lastfm;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.profile.Profile;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class LoginListener implements Listener {


    // TODO: FIX CONSOLE SPAM?

    @EventHandler
    public void onBlockPlaceEvent(AsyncChatEvent event) {
        Player player = event.getPlayer();

        String messageText = PlainTextComponentSerializer.plainText().serialize(event.message());

        if (!messageText.startsWith(".login")) return;
        String[] args = messageText.split(" ");

        event.setCancelled(true);

        if (args.length == 1) {
            event.getPlayer().sendMessage("");
            return;
        }

        String username = args[1];

        String errorMessage = Main.getInstance().getOtherConfigs().getMessages().getString("lastfm.error");
        var mm = MiniMessage.miniMessage();

        if (messageText.length() > 30) {
            if (errorMessage == null) return;
            player.sendMessage(mm.deserialize(errorMessage.replace("<reason>", "Name too long")));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            if (!Main.getInstance().getLastFmHandler().checkUsernameExists(username)) {
                if (errorMessage == null) return;
                player.sendMessage(mm.deserialize(errorMessage.replace("<reason>", "This account does not exist")));
                event.setCancelled(true);
                return;
            }

            Profile profile = Profile.getProfile(player.getUniqueId());

            if (profile.setLastFmUsername(username)) {
                String nameSet = Main.getInstance().getOtherConfigs().getMessages().getString("lastfm.username-set");
                if (nameSet == null) return;
                player.sendMessage(mm.deserialize(nameSet.replace("<lastfm-username>", username)));
                return;
            }

            String alreadyUsed = Main.getInstance().getOtherConfigs().getMessages().getString("lastfm.name-already-used");
            if (alreadyUsed == null) return;
            event.setCancelled(true);
            player.sendMessage(mm.deserialize(alreadyUsed));
        });
    }

}
