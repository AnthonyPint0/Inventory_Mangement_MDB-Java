package org.company;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("inventory_db");
        MongoCollection<Document> collection = database.getCollection("items");

        System.out.println("*********************************** Welcome to Inventory Management System ***********************************\n");
        boolean repeat = true;

        do {
            System.out.println("""
                     Choice what you what to do today:\

                     1. Enter a new item in the inventory\

                     2. Update the quantity of an item\

                     3. Remove an item from the inventory\

                     4. View all items in the inventory\

                     5. Exit
                     """);

            int choice = sc.nextInt();
            System.out.println();
            switch (choice){
                case 1:
                    inventoryManager.addItem(collection);
                    break;

                case 2:
                    inventoryManager.updateQuantity(collection);
                    break;

                case 3:
                    inventoryManager.removeItem(collection);
                    break;

                case 4:
                    inventoryManager.displayItems(collection);
                    break;

                case 5:
                    repeat = false;
                    break;

                default:
                    System.out.println("Invalid Input! Try Again!\n");
                    break;
            }

        } while (repeat);

        System.out.println("Thank You for using our System!!!");
    }
}
