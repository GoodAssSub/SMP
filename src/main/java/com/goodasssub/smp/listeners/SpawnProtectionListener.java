package com.goodasssub.smp.listeners;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.util.PlayerUtil;
import com.goodasssub.smp.util.SpawnUtil;
import org.bukkit.entity.Entity;
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
        if (event.getDamager() instanceof Player damager) {
            if (damager.isOp()) {
                damager.sendMessage("World: " + event.getEntity().getWorld().getName());
                damager.sendMessage("Event: " + event.getClass().toString());
                damager.sendMessage("Spawn Protection damager: " + SpawnUtil.isEntityInSpawnProtection(event.getDamager(), spawnProtectionRadius));
                damager.sendMessage("Spawn Protection damaged: " + SpawnUtil.isEntityInSpawnProtection(event.getEntity(), spawnProtectionRadius));
            }
        } else if (event.getEntity() instanceof Player damaged) {
            if (damaged.isOp()) {

                damaged.sendMessage("World: " + event.getEntity().getWorld().getName());
                damaged.sendMessage("Event: " + event.getClass().toString());
                damaged.sendMessage("Spawn Protection damager: " + SpawnUtil.isEntityInSpawnProtection(event.getDamager(), spawnProtectionRadius));
                damaged.sendMessage("Spawn Protection damaged: " + SpawnUtil.isEntityInSpawnProtection(event.getEntity(), spawnProtectionRadius));
            }

        }


        if (SpawnUtil.isEntityInSpawnProtection(event.getDamager(), spawnProtectionRadius) ||
            SpawnUtil.isEntityInSpawnProtection(event.getEntity(), spawnProtectionRadius)) {
            event.setCancelled(true);
            if (event.getDamager() instanceof Player damager) {
                PlayerUtil.sendMessage(damager, Main.getInstance().getConfig().getString("spawn.protection.message"));
            }

        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) {
            player.sendMessage("World: " + event.getPlayer().getWorld().getName());
            player.sendMessage("Event: " + event.getClass().toString());
            player.sendMessage("Block: " + event.getBlock().getType());
            player.sendMessage("Spawn Protection: " + SpawnUtil.isEntityInSpawnProtection(player, spawnProtectionRadius));
        }

        if (SpawnUtil.isEntityInSpawnProtection(player, spawnProtectionRadius)) {
            event.setCancelled(true);
            PlayerUtil.sendMessage(player, Main.getInstance().getConfig().getString("spawn.protection.message"));
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) {
            player.sendMessage("World: " + event.getPlayer().getWorld().getName());
            player.sendMessage("Event: " + event.getClass().toString());
            player.sendMessage("Block: " + event.getBlock().getType());
            player.sendMessage("Spawn Protection: " + SpawnUtil.isEntityInSpawnProtection(player, spawnProtectionRadius));
        }

        if (SpawnUtil.isEntityInSpawnProtection(player, spawnProtectionRadius)) {
            event.setCancelled(true);
            PlayerUtil.sendMessage(player, Main.getInstance().getConfig().getString("spawn.protection.message"));
        }
    }

    @EventHandler
    public void onBlockIgniteEvent(BlockIgniteEvent event) {
        Entity entity = event.getIgnitingEntity();

        if (entity instanceof Player igniter) {
            if (igniter.isOp()) {
                igniter.sendMessage("World: " + igniter.getWorld().getName());
                igniter.sendMessage("Event: " + event.getClass().toString());
                igniter.sendMessage("Block: " + event.getBlock().getType());
                igniter.sendMessage("Spawn Protection: " + SpawnUtil.isEntityInSpawnProtection(igniter, spawnProtectionRadius));
            }
        }

        if (SpawnUtil.isEntityInSpawnProtection(entity, spawnProtectionRadius)) {
            event.setCancelled(true);

            if (entity instanceof Player igniter) {
                PlayerUtil.sendMessage(igniter, Main.getInstance().getConfig().getString("spawn.protection.message"));
            }
        }
    }

    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent event) {
        if (SpawnUtil.isBlockInSpawnProtection(event.getBlock(), spawnProtectionRadius)) {
            event.setCancelled(true);
        }
    }

}
