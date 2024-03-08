package com.goodasssub.smp.lastfm.listeners;

import com.goodasssub.smp.Main;
import com.goodasssub.smp.profile.Profile;
import com.goodasssub.smp.util.CC;
import com.goodasssub.smp.util.PlayerUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class NowPlayingListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (!message.equals(".fm") && !message.equals(",fm") && !message.equals(".np") && !message.equals(",np")) return;

        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Profile profile = Profile.getProfile(player.getUniqueId().toString());

            if (profile.getLastFmUsername() == null) {
                PlayerUtil.sendMessage(player, "&cYou do not have a lastfm username set. \nSet it with .login <username>");
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
                    event.setCancelled(true);
                    PlayerUtil.sendMessage(player, "&cError getting your most recent track. No API response.");
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

                StringBuilder stringBuilder = new StringBuilder();

                //event.setMessage(event.getMessage());

                stringBuilder.append("\n");
                stringBuilder.append("&f&lUser&7: &6")
                    .append(profile.getLastFmUsername())
                    .append(" &7(").append(player.getDisplayName()).append(")").append("\n");
                stringBuilder.append("&f&lTrack&7: &6").append(trackName).append("\n");
                stringBuilder.append("&f&lArtist&7: &6").append(artistName).append("\n");
                if (!trackName.equals(albumName)) {
                    stringBuilder.append("&f&lAlbum&7: &6").append(albumName);
                }
                stringBuilder.append("\n");

                Bukkit.getServer().broadcastMessage(CC.translate(stringBuilder.toString()));
            } catch (IOException | ParseException e) {
                event.setCancelled(true);
                PlayerUtil.sendMessage(player, "&cError getting your most recent track. API failed.");
                e.printStackTrace();
            }
        });
    }
}
