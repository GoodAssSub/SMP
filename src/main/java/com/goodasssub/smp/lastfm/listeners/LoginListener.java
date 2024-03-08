package com.goodasssub.smp.lastfm.listeners;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.profile.Profile;
import com.goodasssub.smp.util.PlayerUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class LoginListener implements Listener {


    @EventHandler
    public void onBlockPlaceEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!event.getMessage().startsWith(".login")) return;
        String[] args = event.getMessage().split(" ");

        event.setCancelled(true);

        if (args.length == 1) {
            PlayerUtil.sendMessage(player, "&cUsage: .login <Last FM Username>");
            return;
        }

        String username = args[1];

        if (event.getMessage().length() > 30) {
            PlayerUtil.sendMessage(player, "&cError. Name too long.");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            if (Main.getInstance().getLastFmHandler().checkUsernameExists(username)) {
                PlayerUtil.sendMessage(player, "&cThis account does not exist.");
                return;
            }

            Profile profile = Profile.getProfile(player.getUniqueId().toString());

            if (profile.setLastFmUsername(username)) {
                PlayerUtil.sendMessage(player, "&eYour lastfm username has been set to \"" + username + "\"");
                return;
            }

            PlayerUtil.sendMessage(player, "&cSomeone else on the server already has that username set.");
        });
    }

}
