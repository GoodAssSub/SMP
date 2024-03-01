package com.goodasssub.smp.commands;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigCommand implements CommandExecutor {

    Main plugin;

    public ConfigCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CC.translate("&cCommand not found. Try /smp help to find smp commands."));
            return false;
        }

        String commandName = args[0];

        if (commandName.equalsIgnoreCase("help")) {
            sender.sendMessage("&6/smp reload - Reload the config.yml for SMP plugin.");
            return true;
        }

        if (commandName.equalsIgnoreCase("reload")) {
            sender.sendMessage("&eConfig reloading...");
            long startTime = System.currentTimeMillis();
            plugin.reloadConfig();
            long endTime = System.currentTimeMillis();
            sender.sendMessage("&eConfig reloaded after " + (endTime - startTime) + "ms");
            return true;
        }

        return false;
    }
}
