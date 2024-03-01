package com.goodasssub.smp.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class SpawnUtil {

    public static boolean isLocationInSpawnProtection(Location location, int spawnProtectionRadius) {
        if (location.getWorld() == null) {
            return false;
        }

        Location spawnLocation = location.getWorld().getSpawnLocation();
        double distance = spawnLocation.distance(location);
        return distance <= spawnProtectionRadius;
    }
}
