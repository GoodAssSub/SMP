package com.goodasssub.smp.protection;

import com.goodasssub.smp.Main;
import org.bukkit.Location;

public class ProtectionHandler {

    public ProtectionHandler() {
        if (Main.getInstance().getConfig().getInt("spawn.protection.radius") > 0) {
            Main.getInstance().getLogger().info("Initializing Spawn Protection...");
            Main.getInstance().getServer().getPluginManager().registerEvents(new SpawnProtectionListener(), Main.getInstance());
        }
        if (Main.getInstance().getConfig().getBoolean("protection.disable-tnt.enabled")) {
            Main.getInstance().getLogger().info("Initializing TNT Protection...");
            Main.getInstance().getServer().getPluginManager().registerEvents(new TNTListener(), Main.getInstance());
        }
    }

    // Should I makes this non-static :thinking:
    public static boolean isLocationInSpawnProtection(Location location) {
        String protectedWorld = Main.getInstance().getConfig().getString("protection.spawn.world");
        int protectedRadius = Main.getInstance().getConfig().getInt("protection.spawn.radius");

        if (location.getWorld() == null) return false;
        if (!location.getWorld().getName().equals(protectedWorld)) return false;

        double locationX = location.getX();
        double locationZ = location.getZ();

        double distanceX = Math.abs(locationX);
        double distanceZ = Math.abs(locationZ);

        return distanceX <= protectedRadius &&
               distanceZ <= protectedRadius;
    }


}
