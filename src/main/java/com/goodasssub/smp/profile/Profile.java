package com.goodasssub.smp.profile;

import com.goodasssub.smp.Main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Data;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Data
public class Profile {

    private final String uuid;
    private final String lastFmUsername;

    /*
        TODO: make profile mapper
     */

    public static Profile getProfile(String uuid) {

        MongoCollection<Document> profileCollection = Main.getInstance().getDatabase().getProfilesCollection();
        Document profileDocument = profileCollection.find(Filters.eq("uuid", uuid)).first();

        if (profileDocument == null) {
            Document uniqueId = new Document("uuid", uuid);
            profileCollection.insertOne(uniqueId);

            return new Profile(uuid, null);
        }

        return new Profile(uuid, profileDocument.getString("lastFmUsername"));
    }

    public boolean setLastFmUsername(String userName) {
        MongoCollection<Document> profileCollection = Main.getInstance().getDatabase().getProfilesCollection();
        Document profileFMWithName = profileCollection.find(Filters.eq("lastFmUsername", userName)).first();

        if (profileFMWithName != null) {
            return false;
        }

        Document filter = new Document("uuid", this.uuid);
        Document update = new Document("$set", new Document("lastFmUsername", userName));
        UpdateOptions options = new UpdateOptions().upsert(true);

        profileCollection.updateOne(filter, update, options);

        return true;
    }
}
