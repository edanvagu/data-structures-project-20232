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
    //CARPETA EN DONDE SE GUARDARA EL ARCHIVO DEL INVENTARIO (CAMBIAR PARA CADA PERSONA)

    private String folderPath = "./data-structures-project-20232/Wiis/src/Files";
    //NOMBRE DEL ARCHIVO PREDETERMINADO
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
                    break; // Encuentra el producto y sale del bucle
                }
            }

            if (productToUpdate != null) {
                if (transaction.getType().equals("compra")) {
                    // Actualizar la cantidad cuando es una transacción de compra
                    productToUpdate.setAmount(productToUpdate.getAmount() + amount);
                } else if (transaction.getType().equals("venta")) {
                    // Verificar si hay suficientes productos para la venta
                    if (productToUpdate.getAmount() >= amount) {
                        // Actualizar la cantidad cuando es una transacción de venta
                        productToUpdate.setAmount(productToUpdate.getAmount() - amount);
                    } else {
                        // Manejar el caso en el que no hay suficientes productos para la venta
                        System.out.println("No hay suficientes productos para la venta de " + productToUpdate.getName());
                        // Puedes implementar una lógica adicional aquí, como registrar la transacción como fallida.
                    }
                }
            } else {
                // Manejar el caso en el que el producto no se encontró en el inventario
                System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
                // Puedes implementar una lógica adicional aquí, como registrar la transacción como fallida.
            }
        }
    }


    public void printTransactions(){
        for (Transaction t : transactions) {
            List<TransactionDetail> temp = t.getDetails();
            System.out.println("Fecha de Transacción: " + t.getDate());

            for (TransactionDetail details : temp) {
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
            File carpeta = new File(folderPath);
            if (!carpeta.exists()) {
                carpeta.mkdirs();  // Crear la carpeta si no existe
            }
            File archivo = new File(carpeta, this.fileName);
            FileWriter newFile = new FileWriter(archivo);

            for (Transaction t : this.transactions) {
                newFile.write(t.getType() + "," + t.getDate() + "\n");
                for (TransactionDetail details : t.getDetails()) {
                    String productCode = details.getProductCode();
                    int amount = details.getAmount();

                    Product product = inventory.getProductByCode(productCode);

                    if (product != null) {
                        String productName = product.getName();
                        double productPrice = product.getPrice();

                        String productDetails = productCode + "," + productName + "," + productPrice + "," + amount;

                        newFile.write(productDetails + "\n");
                    } else {
                        System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
                    }
                }
                newFile.write("a" + "\n");
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


