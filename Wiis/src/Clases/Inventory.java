package Clases;

import java.util.LinkedList;
import java.util.List;

public class Inventory {
    List<Product> products;

    public Inventory(List items) {
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

    }


    public void loadFile(){

}
