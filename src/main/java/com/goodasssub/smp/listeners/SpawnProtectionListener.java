package com.goodasssub.smp.listeners;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.util.CC;
import com.goodasssub.smp.util.SpawnUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class SpawnProtectionListener  implements Listener {

    int spawnProtectionRadius = Main.getInstance().getConfig().getInt("spawn.protection");

    // TODO: check if water flow
    //@EventHandler
    //public void water()

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            player.sendMessage(CC.translate(
                Objects.requireNonNullElse(Main.getInstance().getConfig().getString("spawn.protection.message"),
                "Spawn protection message not set in config.yml")
            ));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            player.sendMessage(CC.translate(
                Objects.requireNonNullElse(Main.getInstance().getConfig().getString("spawn.protection.message"),
                    "Spawn protection message not set in config.yml")
            ));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlaceBlockEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            event.setCancelled(true);
            player.sendMessage(CC.translate(
                Objects.requireNonNullElse(Main.getInstance().getConfig().getString("spawn.protection.message"),
                    "Spawn protection message not set in config.yml")
            ));

        }
    }

    @EventHandler
    public void onPlayerPlaceBlockEvent(BlockIgniteEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            event.setCancelled(true);

            if (player == null) return;
            player.sendMessage(CC.translate(
                Objects.requireNonNullElse(Main.getInstance().getConfig().getString("spawn.protection.message"),
                    "Spawn protection message not set in config.yml")
            ));
        }
    }
}
