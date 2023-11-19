import InventoryClases.*;
import MenuClases.InventoryManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {


        Inventory inventory = new Inventory();
        //TransactionHistory transactionHistory = new TransactionHistory(inventory);

        inventory.loadFile(); //este método carga el archivo de productos y lo guarda en el hashmap y en el treemap
        //transactionHistory.loadFile();
        //inventory.searchProduct();
        //inventory.updateProduct();
        //inventory.removeProduct();



        //InventoryManager inventoryManager = new InventoryManager(inventory, transactionHistory);

        //inventoryManager.runInventoryMenu();

    }
}