import Clases.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear instancias de Inventory y TransactionHistory sin datos
        Inventory inventory = new Inventory();
        TransactionHistory transactionHistory = new TransactionHistory(inventory);

        // Cargar los datos desde los archivos
        inventory.loadFile();
        transactionHistory.loadFile();

        // Imprimir productos en el inventario y transacciones en el historial
        System.out.println("Productos en el inventario:");
        inventory.printProducts();

        System.out.println("\nTransacciones en el historial:");
        transactionHistory.printTransactions();
    }
}