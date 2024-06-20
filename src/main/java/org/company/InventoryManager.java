package org.company;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManager {

    Scanner sc = new Scanner(System.in);

    Document exitsItem(MongoCollection<Document> collection, String itemName) {
        Bson filter = Filters.eq("name", itemName);
        return collection.find(filter).first();
    }

    void addItem(MongoCollection<Document> collection) {
        System.out.println("Enter item name: ");
        String itemName = sc.nextLine();
        if (exitsItem(collection, itemName) != null) {
            System.out.println("Error: There existing an item with same ITEM NAME.\n");
        } else {
            System.out.println("Enter the quantity of item: ");
            int itemQuantity = sc.nextInt();
            System.out.println("Enter the price of each item: ");
            double itemPrice = sc.nextDouble();

            Document newDocument = new Document("_id", new ObjectId())
                    .append("name", itemName)
                    .append("quantity", itemQuantity)
                    .append("price", itemPrice);
            collection.insertOne(newDocument);
            System.out.println("Item Successfully Added!!!\n");
        }
    }

    void updateQuantity(MongoCollection<Document> collection) {
        System.out.println("Enter item name: ");
        String itemName = sc.nextLine();
        Document item = exitsItem(collection, itemName);
        if (item != null) {
            System.out.println("Enter the new quantity: ");
            int newQuantity = sc.nextInt();
            int currentQuantity = item.getInteger("quantity");

            collection.updateOne(Filters.eq("name", itemName), Updates.set("quantity", newQuantity));

            System.out.println("\nItem's Quantity Updated Successfully!!!");
            System.out.println("\nOld item quantity = " + currentQuantity +"\nNew item quantity = " + newQuantity + "\n");
        }
        else{
            System.out.println("Error: This Item does not exist\n");
        }
    }

    void removeItem(MongoCollection<Document> collection){
        System.out.println("Enter item name: ");
        String itemName = sc.nextLine();
        if (exitsItem(collection, itemName) != null) {
            System.out.println("Error: There existing an item with same ITEM NAME.\n");
        } else {
            collection.deleteOne(Filters.eq("name",itemName));
        }
    }

    void displayItems(MongoCollection<Document> collection) {
        List<Document> documents = collection.find().into(new ArrayList<>());
        System.out.println("Display all items in the inventory:\n");
        int i = 0;
        for (Document docs : documents) {
            System.out.println("Item " + ++i + ": " +
                    "\n\tName: " + docs.getString("name") +
                    "\n\tQuantity: " + docs.getInteger("quantity") +
                    "\n\tPrice: " + docs.getDouble("price") + "\n");
        }
        System.out.println("\n");
    }
}
