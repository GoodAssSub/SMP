package com.goodasssub.smp;

import com.goodasssub.smp.commands.MainCommand;
import com.goodasssub.smp.database.Database;
import com.goodasssub.smp.lastfm.LastFmHandler;
import com.goodasssub.smp.listeners.ChatListener;
import com.goodasssub.smp.profile.listeners.ProfileListener;
import com.goodasssub.smp.scoreboard.Scoreboard;

import com.goodasssub.smp.util.OtherConfigs;
import lombok.Getter;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter private static Main instance;

    @Getter private OtherConfigs otherConfigs;
    @Getter private Database database;
    @Getter private LastFmHandler lastFmHandler;
    @Getter private Scoreboard scoreboard;

    @Override
    public void onEnable() {
        getLogger().info("Starting...");
        instance = this;

        /*
            TODO: adventure api support for minimessages
        */

        // OtherConfigs
        getLogger().info("Initializing config...");
        saveDefaultConfig();

        getLogger().info("Initializing other config...");
        otherConfigs.loadOtherConfigs();
        otherConfigs.saveOtherConfigs();

        // Listeners
        if (getConfig().getBoolean("chat.enabled")) {
            getServer().getPluginManager().registerEvents(new ChatListener(), this);
            getLogger().info("Added Chat Listener...");
        }

        //TODO: i assume right now this needs to be enabled for the plugin to work
        if (getConfig().getBoolean("lastfm.enabled")) {
            this.lastFmHandler = new LastFmHandler(getConfig().getString("lastfm.api-key"));
        }

        // Commands
        this.getCommand("smp").setExecutor(new MainCommand());

        // Scoreboard
        if (getConfig().getBoolean("scoreboard.enabled")) {
            getLogger().info("Initializing Scoreboard...");
            scoreboard = new Scoreboard();
        }

        // MongoDB
        getLogger().info("Connecting to mongodb...");
        this.database = new Database(
            getConfig().getString("mongodb.uri"),
            getConfig().getString("mongodb.database")
        );

        // Profiles
        getLogger().info("Initializing Profiles...");
        Main.getInstance().getServer().getPluginManager().registerEvents(new ProfileListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disconnecting from mongodb...");
        this.database.stop();
    }

    public CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        return CoreProtect;
    }
}
