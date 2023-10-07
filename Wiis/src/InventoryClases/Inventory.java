package InventoryClases;

import java.util.*;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Inventory {
    private String folderPath = System.getProperty("user.dir") + File.separator + "Files";
    private String fileName="inventory.txt";
    private Queue<Product> products;
    private int countProducts = 0;

    public Inventory() {
        this.products = new LinkedList<>();
    }

    public void addProduct(Product product){
        this.products.offer(product);
        countProducts++;
    }

    public void searchAll(){
        long tiempoInicial = System.nanoTime();
        for (Product product : products) {
            if(product.getCode().equals("P9999999")){
                break;
            }
        }
        long tiempoFinal = System.nanoTime();
        long tiempoEjecucion = tiempoFinal - tiempoInicial;

        System.out.println("Tamaño de datos: " + products.size() + ", Tiempo de ejecución: " + tiempoEjecucion + " " +
                "nanosegundos");
    }

    public void updateAll(){
        long tiempoInicial = System.nanoTime();
        for (Product product : products) {
            product.setAmount(product.getAmount()+1);
        }
        long tiempoFinal = System.nanoTime();
        long tiempoEjecucion = tiempoFinal - tiempoInicial;

        System.out.println("Tamaño de datos: " + products.size() + ", Tiempo de ejecución: " + tiempoEjecucion + " " +
                "nanosegundos");
    }
    public void removeAll() {
        long tiempoInicial = System.nanoTime();
        while (!products.isEmpty()) {
            products.poll();
        }
        long tiempoFinal = System.nanoTime();
        long tiempoEjecucion = tiempoFinal - tiempoInicial;

        System.out.println("Tamaño de datos: " + products.size() + ", Tiempo de ejecución: " + tiempoEjecucion + " " +
                "nanosegundos");
    }

    public boolean updateProductName(String productCode, String newName){
        if(getProductByCode(productCode) != null) {
            for (Product product : getProducts()) {
                if (product.getCode().equals(productCode)) {
                    product.setName(newName);
                    return true;
                }
            }
        }
        return false;
    }

    public void updateProductPrice(String productCode, double newPrice){
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price and quantity cannot be negative.");
        }
        for (Product product : getProducts()) {
            if (product.getCode().equals(productCode)) {
                product.setPrice(newPrice);
                break;
            }
        }
    }

    public Product getProductByCode(String productCode) {
        for (Product product : getProducts()) {
            if (product.getCode().equals(productCode)) {
                return product;
            }
        }
        return null;
    }

    public void removeProduct(String productCode){
        for (Product product : getProducts()) {
            if (product.getCode().equals(productCode)) {
                this.products.remove(product);
                break;
            }
        }
    }


    //public void sortInventory(){
        //this.products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
    //}

    /*public void printProducts(){
        this.sortInventory();
        for (Product p : getProducts()) {
            System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getAmount());
        }
    }*/

    public void filterInventoryByPrice(String operator, double price) throws Exception {
        for (Product p : getProducts()) {
            if(operator.equals("less than") && p.getPrice() < price){
                System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getAmount());
            }else if(operator.equals("equals") && p.getPrice() == price){
                System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getAmount());
            }else if(operator.equals("more than") && p.getPrice() > price){
                System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getAmount());
            }
        }
    }

    public void saveFile(){
        try{
            File folder = new File(this.folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileWriter newFile = new FileWriter(this.folderPath+File.separator+this.fileName);
            for (Product p : getProducts()) {
                newFile.write(p.getCode()+"," +p.getName()+","+p.getPrice()+","+p.getAmount()+ "\n");
            }
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file " + this.fileName);
            throw new RuntimeException(e);
        }
    }
    public void loadFile() {

        try {
            File file = new File(this.folderPath+File.separator+this.fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
            //int tamanio = 10000000;
            //long tiempoInicial = System.nanoTime();
            //for (int i = 0; i < tamanio; i++) {
                String[] line = sc.nextLine().split(",");
                String productCode = line[0];
                String productName = line[1];
                double productPrice = Double.parseDouble(line[2]);
                int productAmount = Integer.parseInt(line[3]);

                addProduct(new Product(productCode, productName, productPrice, productAmount));

            }
            //long tiempoFinal = System.nanoTime();
            //long tiempoEjecucion = tiempoFinal - tiempoInicial;

            //System.out.println("Tamaño de datos: " + tamanio + ", Tiempo de ejecución: " + tiempoEjecucion + " " +
              //      "nanosegundos");
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file " + this.fileName);
            throw new RuntimeException(e);
        }
    }

    public void setFileName(String folderName) {
        this.fileName = folderName;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setProducts(Queue<Product> products) {
        this.products = products;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }

    public Queue<Product> getProducts() {
        return this.products;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public int getCountProducts() {
        return countProducts;
    }


}
