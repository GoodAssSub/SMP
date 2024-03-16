package com.goodasssub.smp.lastfm;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.profile.Profile;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class NowPlayingListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncChatEvent event) {
        Component component = event.message();
        String message = PlainTextComponentSerializer.plainText().serialize(component);

        if (!message.equals(".fm") && !message.equals(",fm") && !message.equals(".np") && !message.equals(",np")) return;

        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Profile profile = Profile.getProfile(event.getPlayer().getUniqueId());
            String errorMessage = Main.getInstance().getOtherConfigs().getMessages().getString("lastfm.error");
            var mm = MiniMessage.miniMessage();

            if (profile.getLastFmUsername() == null) {
                String noNameMessage = Main.getInstance().getOtherConfigs().getMessages().getString("lastfm.no-name-set");
                if (noNameMessage != null) {
                    player.sendMessage(mm.deserialize(noNameMessage));
                }

                event.setCancelled(true);
                return;
            }

            OkHttpClient client = new OkHttpClient();

            final String API_URL = Main.getInstance().getLastFmHandler().getAPI_URL();
            final String API_KEY = Main.getInstance().getLastFmHandler().getAPI_KEY();

            String url = API_URL + "?method=user.getrecenttracks&user=" + profile.getLastFmUsername() + "&api_key=" + API_KEY + "&format=json";

            Request request = new Request.Builder()
                .url(url)
                .build();


            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (errorMessage == null) {
                        event.setCancelled(true);
                        return;
                    }

                    player.sendMessage(mm.deserialize(errorMessage.replace("<reason>", "No API response")));
                    return;
                }

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response.body().string());

                JSONObject recentTracks = (JSONObject) jsonObject.get("recenttracks");
                JSONArray tracks = (JSONArray) recentTracks.get("track");
                JSONObject track = (JSONObject) tracks.get(0);
                JSONObject artist = (JSONObject) track.get("artist");
                JSONObject album = (JSONObject) track.get("album");

                String trackName = (String) track.get("name");
                String artistName = (String) artist.get("#text");
                String albumName = (String) album.get("#text");

                List<String> nowPlaying = Main.getInstance().getOtherConfigs().getMessages().getStringList("lastfm.now-playing");

                for (String string : nowPlaying) {
                    if (!trackName.equals(albumName)) {
                        albumName = "None";
                    }
                    string = string.replace("<player>", player.getName());
                    string = string.replace("<track-name>", trackName);
                    string = string.replace("<artist-name>", artistName);
                    string = string.replace("<album-name>", albumName);

                    Bukkit.broadcast(mm.deserialize(string));
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                if (errorMessage == null) {
                    event.setCancelled(true);
                    return;
                }

                player.sendMessage(mm.deserialize(errorMessage.replace("<reason>", "API Failed")));
            }
        });
    }
}
