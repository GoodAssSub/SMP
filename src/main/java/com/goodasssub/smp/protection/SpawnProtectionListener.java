package com.goodasssub.smp.protection;

import com.goodasssub.smp.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class SpawnProtectionListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (ProtectionHandler.isLocationInSpawnProtection(event.getDamager().getLocation()) ||
            ProtectionHandler.isLocationInSpawnProtection(event.getEntity().getLocation())) {

            /*
                TODO: combat logic
             */

            if (event.getDamager() instanceof Player damager) {
                if (damager.getGameMode().equals(GameMode.CREATIVE)) return;
                sendProtectionMessage(damager);
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (ProtectionHandler.isLocationInSpawnProtection(event.getBlock().getLocation())) {
            sendProtectionMessage(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (ProtectionHandler.isLocationInSpawnProtection(event.getBlock().getLocation())) {
            sendProtectionMessage(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(PlayerBucketFillEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (ProtectionHandler.isLocationInSpawnProtection(event.getBlock().getLocation())) {
            sendProtectionMessage(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(PlayerBucketEmptyEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (ProtectionHandler.isLocationInSpawnProtection(event.getBlock().getLocation())) {
            sendProtectionMessage(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Entity entity = event.getIgnitingEntity();

        if (!(entity instanceof Player)) return;
        if (event.getPlayer() == null) return;

        Player igniter = event.getPlayer();

        if (igniter.getGameMode().equals(GameMode.CREATIVE)) return;

        if (ProtectionHandler.isLocationInSpawnProtection(entity.getLocation())) {
            event.setCancelled(true);
            sendProtectionMessage(igniter);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() != null && event.getTarget().getType() == EntityType.PLAYER) {
            if (ProtectionHandler.isLocationInSpawnProtection(event.getTarget().getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        if (ProtectionHandler.isLocationInSpawnProtection(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }
    
    private void sendProtectionMessage(Player player) {
        String protectionMessage = Main.getInstance().getConfig().getString("spawn.protection.message");
        if (protectionMessage != null) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(protectionMessage));
            return;
        }
        Main.getInstance().getLogger().severe("No spawn protection image");
    }

}
