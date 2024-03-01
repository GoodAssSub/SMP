package com.goodasssub.smp.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class CC {
    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
