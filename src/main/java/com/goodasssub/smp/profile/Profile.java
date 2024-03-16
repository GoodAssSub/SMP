package com.goodasssub.smp.profile;

import com.goodasssub.smp.Main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Data;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Profile {

    private final String uuid;
    private final long lastOnline;
    private final String lastFmUsername;

    /*
        TODO: make profile mapper
     */

    public static Profile getProfile(UUID uuid) {
        String uuidString = uuid.toString();

        MongoCollection<Document> profileCollection = Main.getInstance().getDatabase().getProfilesCollection();
        Document profileDocument = profileCollection.find(Filters.eq("uuid", uuid)).first();

        if (profileDocument == null) {
            List<Document> documents = new ArrayList<>();
            documents.add(new Document("uuid", uuid));
            profileCollection.insertMany(documents);

            return new Profile(uuidString, 0, null);
        }

        return new Profile(uuidString,
            profileDocument.getLong("lastOnline") != null ? profileDocument.getLong("lastOnline") : 0,
            profileDocument.getString("lastFmUsername")
        );
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

    public void setLastOnlineTime(long time) {
        MongoCollection<Document> profileCollection = Main.getInstance().getDatabase().getProfilesCollection();

        Document filter = new Document("uuid", this.uuid);
        Document update = new Document("$set", new Document("lastOnline", time));
        UpdateOptions options = new UpdateOptions().upsert(true);

        profileCollection.updateOne(filter, update, options);
    }

    public static long totalProfiles() {
        return Main.getInstance().getDatabase().getProfilesCollection().countDocuments();
    }
}
