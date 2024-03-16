package com.goodasssub.smp.scoreboard;

import com.goodasssub.smp.Main;
import fr.mrmicky.fastboard.adventure.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);

        board.updateTitle(Main.getInstance().getScoreboard().getTitle());
        Main.getInstance().getScoreboard().getBoards().put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FastBoard board = Main.getInstance().getScoreboard().getBoards().remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }
}
