package InventoryClases;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private String folderPath = System.getProperty("user.dir") + File.separator + "Files";
    private String fileName="inventory.txt";
    private ArrayList<Product> products;

    private int countProducts = 0;

    public int getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product){
        this.products.add(product);
        countProducts++;
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

    public void sortInventory(){
        this.products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
    }

    public void printProducts(){
        this.sortInventory();
        for (Product p : getProducts()) {
            System.out.println(p.getCode()+" "+p.getName()+" "+p.getPrice()+" "+p.getAmount());
        }
    }

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
    }

    public void setFileName(String folderName) {
        this.fileName = folderName;
    }
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public ArrayList<Product> getProducts() {
        return this.products;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFolderPath() {
        return folderPath;
    }


}
