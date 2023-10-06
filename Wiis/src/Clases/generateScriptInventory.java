package Clases;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class generateScriptInventory {
    final int cuantity;
    Random random = new Random();
    final String[] names = {"Smartphone", "Laptop", "Tablet","Televisor",
            "Camara", "Auriculares", "Altavoz", "Consola", "Smartwatch",
            "Teclado", "Raton", "Impresora", "Disco_duro", "Router", "Monitor",
            "Cargador", "Proyector", "Dron", "Tarjeta_Grafica", "Reloj_Inteligente"};

    public generateScriptInventory(int cuantity) {
        this.cuantity=cuantity;
    }
    public List<Product> generate(){
        List<Product> products = new LinkedList<>();
        for (int i = 0; i < cuantity; i++) {
            products.add(new Product(String.valueOf(i),String.valueOf(i),i, i));
        }
        return products;
    }
}
