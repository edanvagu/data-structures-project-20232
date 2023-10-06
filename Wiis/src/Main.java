import Clases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Inventory inventory = new Inventory();
        TransactionHistory transactionHistory = new TransactionHistory(inventory);

        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("===== Menú de Gestión de Inventario =====");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Actualizar Nombre de Producto");
            System.out.println("3. Actualizar Precio de Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Realizar Compra");
            System.out.println("6. Realizar Venta");
            System.out.println("7. Imprimir Productos en el Inventario");
            System.out.println("8. Imprimir Transacciones");
            System.out.println("9. Salir");
            System.out.print("Por favor, seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    // Agregar Producto

                    // Implementa la lógica para agregar un nuevo producto al inventario
                    break;
                case 2:
                    // Actualizar Nombre de Producto
                    // Implementa la lógica para actualizar el nombre de un producto
                    break;
                case 3:
                    // Actualizar Precio de Producto
                    // Implementa la lógica para actualizar el precio de un producto
                    break;
                case 4:
                    // Eliminar Producto
                    // Implementa la lógica para eliminar un producto del inventario
                    break;
                case 5:
                    // Realizar Compra
                    // Implementa la lógica para realizar una compra
                    break;
                case 6:
                    // Realizar Venta
                    // Implementa la lógica para realizar una venta
                    break;
                case 7:
                    // Imprimir Productos en el Inventario
                    inventory.printProducts();
                    break;
                case 8:
                    // Imprimir Transacciones
                    transactionHistory.printTransactions();
                    break;
                case 9:
                    // Salir del programa
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }

        // Cierra el scanner
        scanner.close();
    }
}