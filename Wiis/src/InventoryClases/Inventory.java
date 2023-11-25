package InventoryClases;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class Inventory {
    private String folderPath = System.getProperty("user.dir") + File.separator + "Files";
    private String fileName="inventory.txt";
    private HashMap<String, Product> products;
    private TreeMap<String, Product> alphabeticInventory;
    private int countProducts;
    
    public Inventory() {
        this.products = new HashMap<>();
        this.alphabeticInventory = new TreeMap<>();
        this.countProducts = 0;
    }
    
    public void addProduct(Product product) {
        this.products.put(product.getCode(), product);
        alphabeticInventory.put(product.getName(), product);
        countProducts++;
    }
    
    public boolean updateProductName(String productCode, String newName){
        Product productToUpdate = products.get(productCode);
        if(productToUpdate != null) {
            alphabeticInventory.remove(productToUpdate.getName());
            productToUpdate.setName(newName);
            alphabeticInventory.put(newName, productToUpdate);
            return true;
        }
        return false;
    }

    public void updateProductPrice(String productCode, double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        Product productToUpdate = products.get(productCode);
        if (productToUpdate != null) {
            alphabeticInventory.remove(productToUpdate.getName());
            productToUpdate.setPrice(newPrice);
            alphabeticInventory.put(productToUpdate.getName(), productToUpdate);
        }
    }

    public Product getProductByCode(String productCode) {
        return this.products.get(productCode);
    }

    public void removeProduct(String productCode) {
        Product productToRemove = getProductByCode(productCode);
        if (productToRemove != null) {
            alphabeticInventory.remove(productToRemove.getName());
            products.remove(productCode);
        }
    }

    public ArrayList<Product> filterInventoryByPriceRange(double minPrice, double maxPrice) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product p : products.values()) {
            double productPrice = p.getPrice();
            if (productPrice >= minPrice && productPrice <= maxPrice) {
                filteredProducts.add(p);
            }
        }
        return filteredProducts;
    }

    public void saveFile() {
        try {
            File folder = new File(this.folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            FileWriter newFile = new FileWriter(this.folderPath + File.separator + this.fileName);
            for (Product p : products.values()) {
                newFile.write(p.getCode() + "," + p.getName() + "," + p.getPrice() + "," + p.getQuantity() + "\n");
            }
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file " + this.fileName);
            throw new RuntimeException(e);
        }
    }
    
    public void loadFile() {
        try {
            File file = new File(this.folderPath + File.separator + this.fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                String productCode = line[0];
                String productName = line[1];
                double productPrice = Double.parseDouble(line[2]);
                int productAmount = Integer.parseInt(line[3]);
                addProduct(new Product(productCode, productName, productPrice, productAmount));
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file " + this.fileName);
            throw new RuntimeException(e);
        }
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }

    public TreeMap<String, Product> getAlphabeticInventory() {
        return alphabeticInventory;
    }

    public void setAlphabeticInventory(TreeMap<String, Product> alphabeticInventory) {
        this.alphabeticInventory = alphabeticInventory;
    }

    public int getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }
}
