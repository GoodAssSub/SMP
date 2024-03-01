package com.goodasssub.smp.listeners;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.util.CC;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class TNTListener implements Listener {

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent event) {
        if (event.getLocation().getWorld() == null) return;
        if (tntWorldNotInConfig(event.getLocation().getWorld())) return;

        if (event.getEntityType().equals(EntityType.PRIMED_TNT)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (event.getBlock().getLocation().getWorld() == null) return;
        if (tntWorldNotInConfig(event.getBlock().getLocation().getWorld())) return;

        if (event.getBlock().getType().equals(Material.TNT)) {
            event.getPlayer().sendMessage(CC.translate(Main.getInstance().getConfig().getString("disable-tnt.message")));
        }
    }

    private boolean tntWorldNotInConfig(World world) {
        List<String> worldNames = Main.getInstance().getConfig().getStringList("disable-tnt.worlds");
        return !worldNames.contains(world.getName());
    }
}
