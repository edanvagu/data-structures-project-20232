package InventoryClases;

import java.util.*;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Inventory {
    private String folderPath = System.getProperty("user.dir") + File.separator + "Files";
    private String fileName = "inventory.txt";
    private HashMap<String, Product> products;
    private TreeMap<String, Product> alphabeticInventory;
    private int countProducts = 0;
    static final String productCodeTest = "P9999999"; //código de producto para pruebas

    public Inventory() {
        this.products = new HashMap<>();
        this.alphabeticInventory = new TreeMap<>();
    }

    public void addProduct(Product product){
        //HashMap

        this.products.put(product.getCode(), product);

        //TreeMap

        //this.alphabeticInventory.put(product.getName(), product);

    }

    public void searchProduct(String code){
        //HashMap
        
        this.products.get(code); //prueba para buscar en hashmap

        //TreeMap
        //this.alphabeticInventory.get(code); //prueba para buscar en treemap

    }

    public void updateProduct(String code){
        //HashMap

        products.replace(code, products.get(code)); //prueba para actualizar en hashmap

        //TreeMap
        //alphabeticInventory.replace(code, alphabeticInventory.get(code)); //prueba para actualizar en treemap

    }
    
    public void removeProduct(String code) {
        //HashMap
        products.remove(code); //prueba para eliminar en hashmap

        //TreeMap
        //alphabeticInventory.remove(code); //prueba para eliminar en treemap

    }

    public boolean updateProductName(String productCode, String newName){
        if(getProductByCode(productCode) != null) {
            // actualizar el nombre del producto en el mapa
            alphabeticInventory.remove(getProductByCode(productCode).getName());
            alphabeticInventory.put(newName, getProductByCode(productCode));


            // for (Product product : getProducts()) {
            //     if (product.getCode().equals(productCode)) {
            //         product.setName(newName);
            //         return true;
            //     }
            // }
        }
        return false;
    }

    public void updateProductPrice(String productCode, double newPrice){
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price and quantity cannot be negative.");
        }
        // for (Product product : getProducts()) {
        //     if (product.getCode().equals(productCode)) {
        //         product.setPrice(newPrice);
        //         break;
        //     }
        // }
    }

    public Product getProductByCode(String productCode) {
        return this.products.get(productCode);
    }

    public void removeProductByCode(String productCode) {
        Product product = getProductByCode(productCode);
        alphabeticInventory.remove(product.getName());
        if (product != null) {
            this.products.remove(productCode);
        }
    }

    /*public void printProducts(){
        for (String productName : alphabeticInventory.keySet()) {
            System.out.println(alphabeticInventory.get(productName).getCode()+" "+alphabeticInventory.get(productName).getName()+" "+alphabeticInventory.get(productName).getPrice()+" "+alphabeticInventory.get(productName).getQuantity());
        }
    }*/

    public void filterInventoryByPrice(String operator, double price) throws Exception {
        // for (Product p : getProducts()) {
        //     if(operator.equals("less than") && p.getPrice() < price){
        //         System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getQuantity());
        //     }else if(operator.equals("equals") && p.getPrice() == price){
        //         System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getQuantity());
        //     }else if(operator.equals("more than") && p.getPrice() > price){
        //         System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getQuantity());
        //     }
        // }
    }

    public void saveFile(){
        try{
            File folder = new File(this.folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileWriter newFile = new FileWriter(this.folderPath+File.separator+this.fileName);
            // for (Product p : getProducts()) {
            //     newFile.write(p.getCode()+"," +p.getName()+","+p.getPrice()+","+p.getQuantity()+ "\n");
            // }
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file " + this.fileName);
            throw new RuntimeException(e);
        }
    }
    
    public void loadFile( ) {
        int num = 0;
        long tiempoInicial = System.nanoTime();
        try {
            File file = new File(this.folderPath+File.separator+this.fileName);
            Scanner sc = new Scanner(file);
            //for (int i = 0; i < num; i++) {


            while (sc.hasNextLine()) {
                
                String[] line = sc.nextLine().split(",");
                String productCode = line[0];
                String productName = line[1];
                double productPrice = Double.parseDouble(line[2]);
                int productAmount = Integer.parseInt(line[3]);

                addProduct(new Product(productCode, productName, productPrice, productAmount));

            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file " + this.fileName);
            throw new RuntimeException(e);
        }
        long tiempoFinal = System.nanoTime();
        System.out.println("Cantidad de datos = " + num + " y tiempo addProduct = "+ (tiempoFinal - tiempoInicial));
    }    

    public void setFileName(String folderName) {
        this.fileName = folderName;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }

    public HashMap<String, Product> getProducts() {
        return products;
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
