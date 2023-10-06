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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();

            for (int i = 0; i < 1000; i++) {
                String codigo = "P"+i; // Genera un código de 5 caracteres aleatorios
                String nombre = "Product"+i; // Genera un nombre de 10 caracteres aleatorios
                double precio = 10 + random.nextDouble() * 90; // Genera un precio entre 10 y 100
                int cantidad = random.nextInt(100); // Genera una cantidad aleatoria entre 0 y 99

                String linea = codigo + "," + nombre + "," + precio + "," + cantidad;
                writer.write(linea);
                writer.newLine(); // Agrega una nueva línea después de cada dato
            }

            System.out.println("Se generaron 1000 datos y se guardaron en " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomString(int length) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }
}
