import Clases.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        Product product = new Product("001", "Pera", 0.5, 5);
        Product product2 = new Product("002", "Apio", 0.5, 5);



        inventory.addProduct(product);
        inventory.addProduct(product2);
      //  inventory.printProducts();



        TransactionHistory transactions = new TransactionHistory(inventory);

        Transaction t = new Transaction("t001", "compra","10-03-2015");

        t.addTransactionDetail("001", 10);
        t.addTransactionDetail("002", 6);

        transactions.addTransaction(t);
 //       transactions.printTransactions();

        Transaction f = new Transaction("t002", "venta","05-10-2020");

        f.addTransactionDetail("001", 2);
        f.addTransactionDetail("002", 3);

        transactions.addTransaction(f);

        transactions.printTransactions();

        inventory.printProducts();

        transactions.saveFile();

      //  inventory.saveFile();
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