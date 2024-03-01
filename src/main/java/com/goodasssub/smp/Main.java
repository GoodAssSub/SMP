package com.goodasssub.smp;

import com.goodasssub.smp.commands.ConfigCommand;
import com.goodasssub.smp.listeners.TNTListener;
import com.goodasssub.smp.listeners.ChatListener;
import com.goodasssub.smp.scoreboard.ScoreboardAdapter;
import com.goodasssub.smp.listeners.SpawnProtectionListener;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Starting...");

        // Config
        getLogger().info("Initializing Config...");
        saveDefaultConfig();

        // Listeners
        getLogger().info("Initializing Listeners...");
        if (getConfig().getBoolean("disable-tnt.enabled")) {
            getServer().getPluginManager().registerEvents(new TNTListener(this), this);
        }
        if (getConfig().getInt("spawn.protection.radius") > 0) {
            getServer().getPluginManager().registerEvents(new SpawnProtectionListener(this), this);
        }
        if (getConfig().getBoolean("chat-format.enabled")) {
            getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        }

        // Commands
        this.getCommand("kit").setExecutor(new ConfigCommand(this));

        // Scoreboard
        if (getConfig().getBoolean("scoreboard.enabled")) {
            getLogger().info("Initializing Scoreboard...");
            Assemble assemble = new Assemble(this, new ScoreboardAdapter(this));
            assemble.setTicks(20);
            assemble.setAssembleStyle(AssembleStyle.MODERN);
        }

    }

    @Override
    public void onDisable() {

    }
}
