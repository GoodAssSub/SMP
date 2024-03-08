package com.goodasssub.smp.database;

import com.goodasssub.smp.Main;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

public class Database {

    MongoClient client;
    MongoDatabase database;
    @Getter MongoCollection<Document> profilesCollection;

    public Database(String uri, String databaseName) {
        try {
            this.client = MongoClients.create(uri);
            this.database = this.client.getDatabase(databaseName);
            this.profilesCollection = this.database.getCollection("profiles");
        } catch (Exception e) {
            e.printStackTrace();
            Main.getInstance().getLogger().severe("Error connecting to mongodb!");
            System.exit(0);
        }
    }

    public void stop() {
        this.client.close();
    }
}
