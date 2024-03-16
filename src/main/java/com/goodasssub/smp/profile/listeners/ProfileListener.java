package com.goodasssub.smp.profile.listeners;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.profile.Profile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = Main.getInstance().getConfig().getString("chat.join-message");

        if (joinMessage == null) joinMessage = "";
        joinMessage = joinMessage.replace("<player>", event.getPlayer().getName());

        event.joinMessage(MiniMessage.miniMessage().deserialize(joinMessage));

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Player player = event.getPlayer();

            Profile profile = Profile.getProfile(event.getPlayer().getUniqueId());

            if (profile.getLastOnline() == 0) {
                Location spawnLocation = player.getWorld().getSpawnLocation();

                String string = Main.getInstance().getConfig().getString("chat.first-join-message");
                if (string == null) string = "";
                string = string.replace("<unique-players>", String.valueOf(Profile.totalProfiles()));
                string = string.replace("<player>", event.getPlayer().getName());

                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    player.teleport(spawnLocation);
                });

                Bukkit.getServer().broadcast(MiniMessage.miniMessage().deserialize(string));
            }

            profile.setLastOnlineTime(System.currentTimeMillis());
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String leaveMessage = Main.getInstance().getConfig().getString("chat.leave-message");

        if (leaveMessage == null) leaveMessage = "";
        leaveMessage = leaveMessage.replace("<player>", event.getPlayer().getName());

        event.quitMessage(MiniMessage.miniMessage().deserialize(leaveMessage));
    }
}
