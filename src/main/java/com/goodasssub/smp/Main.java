package com.goodasssub.smp;

import com.goodasssub.smp.commands.MainCommand;
import com.goodasssub.smp.database.Database;
import com.goodasssub.smp.lastfm.LastFmHandler;
import com.goodasssub.smp.lastfm.listeners.LoginListener;
import com.goodasssub.smp.lastfm.listeners.NowPlayingListener;
import com.goodasssub.smp.listeners.TNTListener;
import com.goodasssub.smp.listeners.ChatListener;
import com.goodasssub.smp.scoreboard.ScoreboardAdapter;
import com.goodasssub.smp.listeners.SpawnProtectionListener;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Main extends JavaPlugin {

    @Getter private static Main instance;

    @Getter private Database database;
    @Getter private LastFmHandler lastFmHandler;

    @Override
    public void onEnable() {
        getLogger().info("Starting...");
        instance = this;

        /*
            TODO: adventure api support for minimessages
        */

        // Config
        getLogger().info("Initializing Config...");
        saveDefaultConfig();

        // Listeners
        getLogger().info("Initializing Listeners...");
        if (getConfig().getBoolean("disable-tnt.enabled")) {
            getServer().getPluginManager().registerEvents(new TNTListener(), this);
        }
        if (getConfig().getInt("spawn.protection.radius") > 0) {
            getServer().getPluginManager().registerEvents(new SpawnProtectionListener(), this);
        }
        if (getConfig().getBoolean("chat-format.enabled")) {
            getServer().getPluginManager().registerEvents(new ChatListener(), this);
        }
        if (getConfig().getBoolean("lastfm.enabled")) {
            this.lastFmHandler = new LastFmHandler(getConfig().getString("lastfm.api-key"));
        }

        // Commands
        this.getCommand("smp").setExecutor(new MainCommand());

        // Scoreboard
        if (getConfig().getBoolean("scoreboard.enabled")) {
            getLogger().info("Initializing Scoreboard...");
            Assemble assemble = new Assemble(this, new ScoreboardAdapter());
            assemble.setTicks(20);
            assemble.setAssembleStyle(AssembleStyle.MODERN);
        }

        getLogger().info("Connecting to mongodb...");
        this.database = new Database(
            getConfig().getString("mongodb.uri"),
            getConfig().getString("mongodb.database")
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("Disconnecting from mongodb...");
        this.database.stop();
    }
}
