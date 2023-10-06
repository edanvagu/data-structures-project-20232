import Clases.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Inventory inventory = new Inventory(new ArrayList<>());
//        inventory.loadFile();
//        inventory.printProducts();
        Product product = new Product("001", "Pera", 0.5, 5);
        Inventory inventory = new Inventory();
        inventory.addProduct(product);
        inventory.printProducts();



        TransactionHistory tx = new TransactionHistory(inventory);

        Transaction num = new Transaction("compra","10-03-2015");

        num.addTransactionDetail("001", 10);

        tx.addTransaction(num);
        inventory.printProducts();
        inventory.saveFile();
      //  tx.saveFile();


//        ArrayList<Product> productos = new ArrayList<>();
//        productos.add(new Product("001", "Pera", 0.5, 10));
//        productos.add(new Product("002", "Naranja", 0.6, 15));
//        productos.add(new Product("003", "Manzana", 0.7, 12));
//        Inventory inventory = new Inventory(productos);
//        inventory.saveFile();
//        inventory.printProducts();

//        Transaction transaction = new Transaction(true,"10-11-2022");
//
//        transaction.addTransactionDetail(new Product("001", "Pera", 0.5, 10), 10);
//
//        TransactionHistory transactionHistory = new TransactionHistory();
//        transactionHistory.addTransaction(transaction);
//
//        transactionHistory.printTransactions();

    }
}