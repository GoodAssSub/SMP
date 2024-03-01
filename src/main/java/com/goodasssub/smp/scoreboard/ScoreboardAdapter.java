package com.goodasssub.smp.scoreboard;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.util.CC;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
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
        return new ArrayList<>(Main.getInstance().getConfig().getStringList("scoreboard.lines"));
    }

}