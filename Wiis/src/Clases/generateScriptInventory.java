package Clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class generateScriptInventory {

    public static void main(String[] args) {
        String folderPath = System.getProperty("user.dir") + File.separator + "Files";
        String fileName = folderPath + "/prueba.txt";

        int cases = 1000;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();

            for (int i = 0; i < cases; i++) {
                String codigo = "P"+i; // Genera un código de 5 caracteres aleatorios
                String nombre = "Product"+i; // Genera un nombre de 10 caracteres aleatorios
                double precio = 10 + random.nextDouble() * 90; // Genera un precio entre 10 y 100
                int cantidad = random.nextInt(100); // Genera una cantidad aleatoria entre 0 y 99

                String linea = codigo + "," + nombre + "," + precio + "," + cantidad;
                writer.write(linea);
                writer.newLine(); // Agrega una nueva línea después de cada dato
            }

            System.out.println("Se generaron "+cases+" datos y se guardaron en " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
