import Clases.Inventory;
import Clases.Product;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> productos = new ArrayList<>();
        productos.add(new Product("001", "Pera", 0.5, 10));
        productos.add(new Product("002", "Naranja", 0.6, 15));
        productos.add(new Product("003", "Manzana", 0.7, 12));
        Inventory inventory = new Inventory(productos);

        inventory.printProducts();
    }
}