package Clases;

import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class TransactionHistory {

    private List<Transaction> transactions;
    private Inventory inventory;
    private String folderPath = System.getProperty("user.dir") + File.separator + "Files";
    private String fileName = "transactionHistory.txt";

    public TransactionHistory(Inventory inventory) {
        this.transactions = new LinkedList<>();
        this.inventory = inventory;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);

        for (TransactionDetail detail : transaction.getDetails()) {
            String productCode = detail.getProductCode();
            int amount = detail.getAmount();

            Product productToUpdate = null;
            for (Product product : inventory.getProducts()) {
                if (product.getCode().equals(productCode)) {
                    productToUpdate = product;
                    break;
                }
            }
            if (productToUpdate != null) {
                if (transaction.getType().equals("compra")) {
                    productToUpdate.setAmount(productToUpdate.getAmount() + amount);
                } else if (transaction.getType().equals("venta")) {
                    if (productToUpdate.getAmount() >= amount) {
                        productToUpdate.setAmount(productToUpdate.getAmount() - amount);
                    } else {
                        System.out.println("No hay suficientes productos para la venta de " + productToUpdate.getName());
                    }
                }
            } else {
                System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
            }
        }
    }


    public void printTransactions(){
        for (Transaction t : transactions) {
            System.out.println("Fecha de Transacción: " + t.getDate());
            System.out.println("Tipo de Transacción: " + t.getType());

            for (TransactionDetail details : t.getDetails()) {
                String productCode = details.getProductCode();
                int amount = details.getAmount();

                System.out.println("Código de Producto: " + productCode);
                System.out.println("Cantidad involucrada: " + amount);
            }
        }
    }

    public void printTransactions(String initial, String finalDate){

    }

    public void saveFile(){
        try{
            File folder = new File(this.folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileWriter newFile = new FileWriter(this.folderPath+File.separator+this.fileName);

            for (Transaction t : this.transactions) {
                for (TransactionDetail details : t.getDetails()) {
                        newFile.write(t.getTransactionCode() + "," + t.getDate() + "," + t.getType() + "," + details.getProductCode() + "," + details.getAmount() + "\n");
                }
            }
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file " + this.fileName);
            throw new RuntimeException(e);
        }

    }
    public void loadFile(){
        try {
            File file = new File(this.folderPath+File.separator+this.fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                String transactionCode = line[0];
                String transactionDate = line[1];
                String transactionType = line[2];
                String productCode = line[3];
                int productAmount = Integer.parseInt(line[4]);

                // Crea una nueva transacción si es diferente del código de transacción anterior
                if (transactions.isEmpty() || !transactionCode.equals(transactions.get(transactions.size() - 1).getTransactionCode())) {
                    Transaction transaction = new Transaction(transactionCode, transactionType, transactionDate);
                    transactions.add(transaction);
                }
                // Agrega un detalle de transacción a la última transacción
                transactions.get(transactions.size() - 1).addTransactionDetail(productCode, productAmount);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file " + this.fileName);
            throw new RuntimeException(e);
        }

    }
    public String getFileName(){
        return this.fileName;
    }
    public String getFolderPath(){
        return this.folderPath;
    }
    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

}


