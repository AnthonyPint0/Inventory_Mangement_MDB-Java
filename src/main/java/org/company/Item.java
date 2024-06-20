package org.company;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;


public class Item {
    public static void main(String[] args){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase inventory_db = mongoClient.getDatabase("inventory_db");
        MongoCollection<Document> itemsCollection = inventory_db.getCollection("items");

        System.out.println(getId(itemsCollection,"Laptop"));
        System.out.println();
        System.out.println(getName(itemsCollection,"6673bb8bbabff809e05cf67c"));
        System.out.println();
        System.out.println(getQuantity(itemsCollection,null,"Laptop"));
        System.out.println();
        System.out.println(getQuantity(itemsCollection,"6673bb8bbabff809e05cf67c",null));
        System.out.println();
        System.out.println(getPrice(itemsCollection,null,"Laptop"));
        System.out.println();
        System.out.println(getPrice(itemsCollection,"6673bb8bbabff809e05cf67c",null));

    }

    public static ObjectId getId(MongoCollection<Document> itemsCollection,String itemName) {
        Document docs = itemsCollection.find(Filters.eq("name", itemName)).first();
        assert docs != null;
        return docs.getObjectId("_id");
    }

    public static String getName(MongoCollection<Document> itemsCollection,String objectStr) {
        ObjectId objectId = new ObjectId(objectStr);
        Document docs = itemsCollection.find(Filters.eq("_id", objectId)).first();
        assert docs != null;
        return docs.getString("name");
    }

    public static int getQuantity(MongoCollection<Document> itemsCollection,String objectStr,String nameStr) {
        if ((nameStr != null) && (objectStr == null)) {
            Document docs = itemsCollection.find(Filters.eq("name", nameStr)).first();
            assert docs != null;
            return docs.getInteger("quantity");
        } else if ((nameStr == null) && (objectStr != null)) {
            ObjectId objectId = new ObjectId(objectStr);
            Document docs = itemsCollection.find(Filters.eq("_id", objectId)).first();
            assert docs != null;
            return docs.getInteger("quantity");
       }
        return -404;
    }

    public static double getPrice(MongoCollection<Document> itemsCollection,String objectStr,String nameStr) {
        if ((nameStr != null) && (objectStr == null)) {
            Document docs = itemsCollection.find(Filters.eq("name", nameStr)).first();
            assert docs != null;
            return docs.getDouble("price");
        } else if ((nameStr == null) && (objectStr != null)) {
            ObjectId objectId = new ObjectId(objectStr);
            Document docs = itemsCollection.find(Filters.eq("_id", objectId)).first();
            assert docs != null;
            return docs.getDouble("price");
        }
        return -404;
    }
}

