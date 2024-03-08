package com.goodasssub.smp.scoreboard;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.util.CC;
import com.goodasssub.smp.util.SpawnUtil;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreboardAdapter implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return CC.translate(Objects.requireNonNullElse(Main.getInstance().getConfig().getString("scoreboard.title"), "None"));
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = Main.getInstance().getConfig().getStringList("scoreboard.lines");

        /*
            TODO: %server-ip% placeholder
         */

        // lazy
        for (int i = 0; i < lines.size(); i++) {
            String original = lines.get(i);

            String onlinePlayers = String.valueOf(Bukkit.getOnlinePlayers().size());
            String replaced = original.replace("%online-players%", onlinePlayers);

            int spawnProtectionRadius = Main.getInstance().getConfig().getInt("spawn.protection.radius");
            boolean spawnProtected = SpawnUtil.isEntityInSpawnProtection(player, spawnProtectionRadius);
            replaced = replaced.replace("%spawn-protection%", spawnProtected ? "&eEnabled" : "&cDisabled");

            lines.set(i, replaced);
        }

        return lines;
    }

}