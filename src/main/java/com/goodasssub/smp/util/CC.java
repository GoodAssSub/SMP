package com.goodasssub.smp.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class CC {
    public static String translate(@NotNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
