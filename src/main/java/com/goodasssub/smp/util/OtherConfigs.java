package com.goodasssub.smp.util;

import com.goodasssub.smp.Main;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class OtherConfigs {
    @Getter
    private FileConfiguration messages;
    private File messagesFile;

    public void loadOtherConfigs() {
        File dataFolder = Main.getInstance().getDataFolder();

        messagesFile = new File(dataFolder, "messages.yml");
        if (!messagesFile.exists()) {
            Main.getInstance().saveResource("messages.yml", false);
        }

        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void saveOtherConfigs() {
        try {
            messages.save(messagesFile);
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Failed to save messages.yml!");
            e.printStackTrace();
        }
    }
}