package Scripts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class gScriptTransactionHistory {

    public static void main(String[] args) {
        String folderPath = System.getProperty("user.dir") + File.separator + "Files";

        String fileName = folderPath + File.separator + "transactionHistory.txt";

        int cases=5;
        String[] types = {"compra","venta"};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();
            for (int i = 0; i < cases; i++) {
                String TransactionID = "T"+i;

                int day = random.nextInt(30); // 0-29
                int month = random.nextInt(12) + 1; // 1-12
                int year = random.nextInt(24) + 2000; // 2000-2023
                String TransactionDate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
                String TransactionType = types[random.nextInt(2)];
                int cantidadProductos= random.nextInt(20)+1;
                //Tener en cuenta para la segunda entrega del proyecto que en el historial de transacciones no puede
                // haber productos que no estén en el inventario. Es decir, el código del producto debe ir hasta,
                // como máximo, el mismo valor del último producto del inventario.
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
