package com.goodasssub.smp.lastfm;

import com.goodasssub.smp.Main;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.Bukkit;

import java.io.IOException;

public class LastFmHandler {

    @Getter private final String API_URL = "https://ws.audioscrobbler.com/2.0/";
    @Getter private final String API_KEY;

    public LastFmHandler(String apiKey) {
        Main.getInstance().getLogger().info("Testing LastFM API key");
        this.API_KEY = apiKey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(API_URL + "?method=user.getinfo&user=rj&api_key=" + API_KEY)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Main.getInstance().getLogger().severe("Invalid LastFM API key. Disabling LastFM integration.");
                return;
            }

            Bukkit.getPluginManager().registerEvents(new LoginListener(), Main.getInstance());
            Bukkit.getServer().getPluginManager().registerEvents(new NowPlayingListener(), Main.getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUsernameExists(String username) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url(API_URL + "?method=user.getinfo&user=" + username +"&api_key=" + API_KEY + "&format=json")
            .build();

        System.out.println();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful() && response.body() != null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
