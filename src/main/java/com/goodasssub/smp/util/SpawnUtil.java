package com.goodasssub.smp.util;

import com.goodasssub.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class SpawnUtil {

    public static boolean isLocationInSpawnProtection(Location location, int spawnProtectionRadius) {
        String worldConfig = Main.getInstance().getConfig().getString("spawn.protection.world");

        if (worldConfig == null) {
            System.out.println("World config null");
            return false;
        }

        World spawnWorld = Bukkit.getWorld(worldConfig);

        if (spawnWorld == null) {
            System.out.println("Spawn world null");
            return false;
        }

        Location spawnLocation = spawnWorld.getSpawnLocation();

        double distance = spawnLocation.distance(location);
        return distance <= spawnProtectionRadius;
    }
}
