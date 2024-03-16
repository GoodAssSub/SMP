package com.goodasssub.smp.commands;

import com.goodasssub.smp.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        var mm = MiniMessage.miniMessage();

        if (args.length == 0) {
            sender.sendMessage(mm.deserialize("<red>Command not found. Try /smp help to find smp commands.</red>"));
            return false;
        }

        String commandName = args[0];

        if (commandName.equalsIgnoreCase("help")) {
            sender.sendMessage(mm.deserialize("<gold>/smp reload - Reload the config.yml for Main plugin.</gold>"));
            return true;
        }

        if (!sender.hasPermission("smp.temp")) {
            sender.sendMessage(mm.deserialize("<red>No permission.</red>"));
            return false;
        };

        if (commandName.equalsIgnoreCase("reload")) {
            sender.sendMessage(mm.deserialize("<green>Config Reloading...</green>"));
            long startTime = System.currentTimeMillis();
            Main.getInstance().reloadConfig();
            long endTime = System.currentTimeMillis();
            sender.sendMessage(mm.deserialize("<green>&eConfig reloaded after " + (endTime - startTime) + "ms</green>"));
            return true;
        }

        return false;
    }
}
