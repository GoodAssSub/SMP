package com.goodasssub.smp.scoreboard;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.protection.ProtectionHandler;
import fr.mrmicky.fastboard.adventure.FastBoard;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Scoreboard {
    @Getter private final Map<UUID, FastBoard> boards = new HashMap<>();

    public Scoreboard() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            for (FastBoard board : this.boards.values()) {
                updateBoard(board);
            }
        }, 20, 20);
        Bukkit.getPluginManager().registerEvents(new ScoreboardListener(), Main.getInstance());
    }

    public Component getTitle() {
        String title = Objects.requireNonNullElse(Main.getInstance().getConfig().getString("scoreboard.title"), "None");
        return MiniMessage.miniMessage().deserialize(title);
    }

    private void updateBoard(FastBoard board) {
        Player player = board.getPlayer();
        var mm = MiniMessage.miniMessage();

        List<Component> lines = new ArrayList<>();

        for (String string : Main.getInstance().getConfig().getStringList("scoreboard.lines")) {
            boolean spawnProtected = ProtectionHandler.isLocationInSpawnProtection(player.getLocation());
            String spawnProtectionText = spawnProtected ? "<green>Enabled</green>" : "<red>Disabled</red>";
            string = string.replace("<spawn-protection>", spawnProtectionText);

            string = string.replace("<players>", String.valueOf(Bukkit.getOnlinePlayers().size()));

            lines.add(mm.deserialize(string));
        }

        board.updateLines(lines);
    }

}