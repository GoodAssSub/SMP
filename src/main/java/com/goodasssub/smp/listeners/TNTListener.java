package com.goodasssub.smp.listeners;

import com.goodasssub.smp.Main;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class TNTListener implements Listener {

    Main plugin;

    public TNTListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onEntityExplodeEvent(EntityExplodeEvent event) {
        if (event.getLocation().getWorld() == null) return;
        if (!worldInConfig(event.getLocation().getWorld())) return;

        if (event.getEntityType().equals(EntityType.PRIMED_TNT)) {
            event.setCancelled(true);
        }
    }

    private boolean worldInConfig(World world) {
        List<String> worldNames = plugin.getConfig().getStringList("disable-tnt.worlds");
        return worldNames.contains(world.getName());
    }
}
