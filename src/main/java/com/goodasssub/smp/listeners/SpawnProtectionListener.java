package com.goodasssub.smp.listeners;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.util.PlayerUtil;
import com.goodasssub.smp.util.SpawnUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SpawnProtectionListener implements Listener {

    int spawnProtectionRadius = Main.getInstance().getConfig().getInt("spawn.protection");

    // TODO:
    //  check if water flow
    //  maybe check if player is in spawn prot too

    @EventHandler
    public void onBlockPlaceEvent(EntityDamageByEntityEvent event) {

        Location location1 = event.getDamager().getLocation();
        Location location2 = event.getEntity().getLocation();

        if (event.getDamager() instanceof Player damager) {
            if (damager.isOp()) {
                damager.sendMessage("World: " + event.getEntity().getWorld().getName());
                damager.sendMessage("Event: " + event.getClass().toString());
                damager.sendMessage("Spawn Protection damager: " + SpawnUtil.isLocationInSpawnProtection(event.getDamager().getLocation(), spawnProtectionRadius));
                damager.sendMessage("Spawn Protection damaged: " + SpawnUtil.isLocationInSpawnProtection(event.getEntity().getLocation(), spawnProtectionRadius));
            }
        } else if (event.getEntity() instanceof Player damaged) {
            if (damaged.isOp()) {
                damaged.sendMessage("World: " + event.getEntity().getWorld().getName());
                damaged.sendMessage("Event: " + event.getClass().toString());
                damaged.sendMessage("Spawn Protection damager: " + SpawnUtil.isLocationInSpawnProtection(event.getDamager().getLocation(), spawnProtectionRadius));
                damaged.sendMessage("Spawn Protection damaged: " + SpawnUtil.isLocationInSpawnProtection(event.getEntity().getLocation(), spawnProtectionRadius));
            }

        }


        if (SpawnUtil.isLocationInSpawnProtection(location1, spawnProtectionRadius) ||
            SpawnUtil.isLocationInSpawnProtection(location2, spawnProtectionRadius)) {
            event.setCancelled(true);
            if (event.getDamager() instanceof Player damager) {
                PlayerUtil.sendMessage(damager, Main.getInstance().getConfig().getString("spawn.protection.message"));
            }

        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (player.isOp()) {
            player.sendMessage("World: " + event.getPlayer().getWorld().getName());
            player.sendMessage("Event: " + event.getClass().toString());
            player.sendMessage("Block: " + event.getBlock().getType());
            player.sendMessage("Spawn Protection: " + SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius));
        }

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            event.setCancelled(true);
            PlayerUtil.sendMessage(player, Main.getInstance().getConfig().getString("spawn.protection.message"));
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (player.isOp()) {
            player.sendMessage("World: " + event.getPlayer().getWorld().getName());
            player.sendMessage("Event: " + event.getClass().toString());
            player.sendMessage("Block: " + event.getBlock().getType());
            player.sendMessage("Spawn Protection: " + SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius));
        }

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            event.setCancelled(true);
            PlayerUtil.sendMessage(player, Main.getInstance().getConfig().getString("spawn.protection.message"));
        }
    }

    @EventHandler
    public void onBlockIgniteEvent(BlockIgniteEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (player != null) {
            if (player.isOp()) {
                player.sendMessage("World: " + event.getPlayer().getWorld().getName());
                player.sendMessage("Event: " + event.getClass().toString());
                player.sendMessage("Block: " + event.getBlock().getType());
                player.sendMessage("Spawn Protection: " + SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius));
            }
        }

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            event.setCancelled(true);

            if (player == null) return;
            PlayerUtil.sendMessage(player, Main.getInstance().getConfig().getString("spawn.protection.message"));
        }
    }

    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent event) {
        Location location = event.getBlock().getLocation();

        if (SpawnUtil.isLocationInSpawnProtection(location, spawnProtectionRadius)) {
            event.setCancelled(true);
        }
    }

}
