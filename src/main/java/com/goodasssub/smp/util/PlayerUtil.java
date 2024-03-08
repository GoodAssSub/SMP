package com.goodasssub.smp.util;

import org.bukkit.entity.Player;

public class PlayerUtil {

    /*
        TODO: maybe in the future make this a Profile function
     */
    public static void sendMessage(Player player, String... strings) {

        /*
            TODO: Use Streams
         */

        // what the hell
        for (int i = 0; i < strings.length; i++) {
            String newString = CC.translate(strings[i]);
            if (i != strings.length - 1) {
                newString += "\n";
            }
            strings[i] = newString;
        }

        player.sendMessage(strings);
    }
}
