package Clases;

import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
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


