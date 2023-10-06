package Clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class gScriptTransactionHistory {

    public static void main(String[] args) {
        String folderPath = System.getProperty("user.dir") + File.separator + "Files";
        String fileName = folderPath+File.separator+"pruebaHistory.txt";
        int cases=1000;
        String[] types = {"compra","venta"};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();
            for (int i = 0; i < cases; i++) {
                String TransactionID = "T"+i;
                int day = random.nextInt(30); // 0-29
                int month = random.nextInt(12) + 1; // 1-12
                int year = random.nextInt(24) + 2000; // 2000-2023
                String TransactionDate = String.valueOf(day)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
                String TransactionType = types[random.nextInt(2)];
                int cantidadProductos= random.nextInt(20)+1;
                for (int j = 0; j < cantidadProductos; j++) {
                    String productCode = "P"+j;
                    int cantidad = random.nextInt(100)+1;
                    String linea = TransactionID+","+TransactionDate+","+TransactionType+","+productCode+","+cantidad;
                    writer.write(linea);
                    writer.newLine();
                }
            }

            System.out.println("Se generaron "+cases+" transacciones y se guardaron en " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
