package Clases;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Inventory {
    //CARPETA EN DONDE SE GUARDARA EL ARCHIVO DEL INVENTARIO (CAMBIAR PARA CADA PERSONA)
    private String folderPath="C:\\Users\\Angel\\IdeaProjects\\Proyecto\\data-structures-project-20232\\Wiis\\src\\Files";
    //NOMBRE DEL ARCHIVO PREDETERMINADO
    private String fileName="inventory.txt";
    private ArrayList<Product> products;

    public Inventory(ArrayList<Product> items) {
        this.products = items;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void updateProductName(Product product, String newName){
        for (Product  p : products) {
            if (p.getName().equals(product.getName())){
                p.setName(newName);
                break;
            }
        }
    }

    public void removeProduct(Product product){
        for (Product  p : products) {
            if (p.getName().equals(product.getName())){
                products.remove(p);
            }
        }
    }

    public void printProducts(){
        products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        for (Product p : products) {
            System.out.println(p.getName());
        }
    }

    public void saveFile(){
        try{
            FileWriter newFile = new FileWriter(this.folderPath+File.separator+this.fileName);
            for (Product p : products) {
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
                String code = line[0];
                String name = line[1];
                double price = Double.parseDouble(line[2]);
                int amount = Integer.parseInt(line[3]);
                this.products.add(new Product(code, name, price, amount));
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
        return products;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFolderPath() {
        return folderPath;
    }


}
