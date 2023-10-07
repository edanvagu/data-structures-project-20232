import InventoryClases.*;
import MenuClases.InventoryManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("asdfasdf");

        Inventory inventory = new Inventory();
        TransactionHistory transactionHistory = new TransactionHistory(inventory);

        //inventory.loadFile();
        //transactionHistory.loadFile();

        InventoryManager inventoryManager = new InventoryManager(inventory, transactionHistory);

        //inventoryManager.runInventoryMenu();

    }
}