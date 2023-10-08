import InventoryClases.*;
import MenuClases.InventoryManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {


        Inventory inventory = new Inventory();
        TransactionHistory transactionHistory = new TransactionHistory(inventory);

        inventory.loadFile();
        //transactionHistory.loadFile();
        //inventory.removeAll();
        //inventory.updateAll();
        inventory.searchAll();


        InventoryManager inventoryManager = new InventoryManager(inventory, transactionHistory);

        //inventoryManager.runInventoryMenu();

    }
}