package com.goodasssub.smp.util;

import com.goodasssub.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class SpawnUtil {

    public static boolean isEntityInSpawnProtection(Entity entity, int spawnProtectionRadius) {
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

        if (!entity.getWorld().equals(spawnWorld)) {
            System.out.println("entity not equal block");
            System.out.println("World1 " + entity.getWorld().getName());
            System.out.println("World2 " + spawnWorld.getName());
            System.out.println();
            return false;
        }

        Location spawnLocation = spawnWorld.getSpawnLocation();

        double distance = spawnLocation.distance(entity.getLocation());
        return distance <= spawnProtectionRadius;
    }

    public static boolean isBlockInSpawnProtection(Block block, int spawnProtectionRadius) {
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


        if (!block.getWorld().equals(spawnWorld)) {
            System.out.println("worlds not equal block");
            System.out.println("World1 " + block.getWorld().getName());
            System.out.println("World2 " + spawnWorld.getName());
            System.out.println();
            return false;
        }

        Location spawnLocation = spawnWorld.getSpawnLocation();

        double distance = spawnLocation.distance(block.getLocation());
        return distance <= spawnProtectionRadius;
    }
}
