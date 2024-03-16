package com.goodasssub.smp.protection;

import com.goodasssub.smp.Main;
import io.papermc.paper.event.player.PlayerOpenSignEvent;
import net.coreprotect.CoreProtectAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class SignListener implements Listener {
    @EventHandler
    public void onPlayerOpenSign(PlayerOpenSignEvent event) {
        if (!Main.getInstance().getConfig().getBoolean("protection.sign.prevent-edit-others")) return;
        if (event.getSign().getWorld().getName().equals("world")) return; // fuckkkiitt we assume its world out here gangy
        if (!event.getCause().equals(PlayerOpenSignEvent.Cause.INTERACT)) return;

        CoreProtectAPI coreProtect = Main.getInstance().getCoreProtect();
        if (coreProtect == null) return;

        int time = (int) (System.currentTimeMillis() / 1000L);
        List<String[]> lookup = coreProtect.blockLookup(event.getSign().getBlock(), time);

        if (lookup == null) return;

        boolean canEdit = true;
        for (String[] strings : lookup) {
            CoreProtectAPI.ParseResult parseResult = coreProtect.parseResult(strings);
            if (parseResult.getActionId() != 1) continue;

            canEdit = parseResult.getPlayer().equals(event.getPlayer().getName());

            break;
        }

        if (!canEdit) {
            event.setCancelled(true);
            String message = Main.getInstance().getOtherConfigs().getMessages().getString("protection.prevent-edit-others");

            if (message== null) {
                Main.getInstance().getLogger().severe("No prevent edit others message");
                return;
            }

            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(message));
        }

    }

}
