package MenuClases;

import InventoryClases.*;


import java.util.Scanner;

public class InventoryManager {

    Inventory inventory;
    TransactionHistory transactionHistory;
    public InventoryManager(Inventory inventory, TransactionHistory transactionHistory) {
        this.inventory = inventory;
        this.transactionHistory = transactionHistory;
    }

    public void runInventoryMenu() {

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
            System.out.println("9. Mostrar los 3 productos más vendidos");
            System.out.println("10. Salir");
            System.out.print("Por favor, seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    // Agregar Producto

                    addProduct();
                    break;

                case 2:

                    updateProductName();
                    break;

                case 3:

                    updateProductPrice();
                    break;

                case 4:

                    removeProduct();
                    break;

                case 5:
                    // Realizar Compra
                    // Implementa la lógica para realizar una compra

                    makePurchase();
                    break;

                case 6:
                    // Realizar Venta
                    // Implementa la lógica para realizar una venta

                    makeSale();
                    break;
                case 7:

                    inventory.printProducts();
                    break;

                case 8:

                    transactionHistory.printTransactions();
                    break;

                case 9:
                    //Mostrar los 3 productos más vendidos
                    transactionHistory.getMostSoldProducts();
                    break;
                
                case 10:
                    // Salir del programa
                    inventory.saveFile();
                    transactionHistory.saveFile();
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }

        // Cierra el scanner
        scanner.close();
    }

    public void addProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escriba el nombre del producto");
        String productName = scanner.nextLine(); // Consumir el salto de línea
        System.out.println("Escriba el precio del producto");
        double productPrice = scanner.nextDouble(); // Consumir el salto de línea
        System.out.println("Escriba la cantidad del producto");
        int productAmount = scanner.nextInt(); // Consumir el salto de línea

        inventory.addProduct(new Product("P" + String.valueOf(inventory.getCountProducts()), productName,
                productPrice,
                productAmount));
    }

    public void updateProductName() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Ingrese el código del producto que desea actualizar (o '0' para regresar): ");
            String productCode = scanner.nextLine();

            if (productCode.equals("0")) {
                break; // Regresar al menú principal
            }

            Product productToUpdate = inventory.getProductByCode(productCode);

            if (productToUpdate != null) {
                System.out.print("Ingrese el nuevo nombre para el producto: ");
                String newName = scanner.nextLine();
                productToUpdate.setName(newName);
                System.out.println("Nombre del producto actualizado con éxito.");
            } else {
                System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
            }
        }
    }

    public void updateProductPrice() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Ingrese el código del producto cuyo precio desea actualizar (o '0' para regresar): ");
            String productCode = scanner.nextLine();

            if (productCode.equals("0")) {
                break; // Regresar al menú principal
            }

            Product productToUpdate = inventory.getProductByCode(productCode);

            if (productToUpdate != null) {
                System.out.print("Ingrese el nuevo precio para el producto: ");
                double newPrice = scanner.nextDouble();
                scanner.nextLine(); // Consumir el salto de línea

                // Verificar que el nuevo precio sea mayor o igual a cero
                if (newPrice >= 0) {
                    productToUpdate.setPrice(newPrice);
                    System.out.println("Precio del producto actualizado con éxito.");
                } else {
                    System.out.println("El precio debe ser mayor o igual a cero.");
                }
            } else {
                System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
            }
        }
    }

    //REVISAR DE CÓMO SE ESTÁ GENERANDO LA FECHA EN LAS TRANSACCIONES
    //REVISAR QUE LAS TRANSACCIONES QUE SE HAGAN EN UNA MISMA FECHA QUEDEN AGRUPADAS.
    public void removeProduct() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Ingrese el código del producto que desea eliminar (o '0' para regresar): ");
            String productCode = scanner.nextLine();

            if (productCode.equals("0")) {
                break; // Regresar al menú principal
            }

            Product productToRemove = inventory.getProductByCode(productCode);

            if (productToRemove != null) {
                // Confirmar la eliminación
                System.out.println("¿Está seguro de que desea eliminar el siguiente producto?");
                System.out.println("Código: " + productToRemove.getCode());
                System.out.println("Nombre: " + productToRemove.getName());
                System.out.println("Precio: " + productToRemove.getPrice());
                System.out.println("Cantidad: " + productToRemove.getAmount());
                System.out.print("Ingrese 's' para confirmar la eliminación o 'n' para cancelar: ");
                String confirmation = scanner.nextLine().toLowerCase();

                if (confirmation.equals("s")) {
                    inventory.removeProduct(productCode);
                    System.out.println("Producto eliminado con éxito.");
                } else if (confirmation.equals("n")) {
                    System.out.println("Eliminación cancelada.");
                } else {
                    System.out.println("Opción no válida. Debe ingresar 's' para confirmar o 'n' para cancelar.");
                }
            } else {
                System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
            }
        }
    }

    public void makePurchase() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Ingrese el código del producto que desea comprar (o '0' para regresar): ");
            String productCode = scanner.nextLine();

            if (productCode.equals("0")) {
                break; // Regresar al menú principal
            }

            Product productToPurchase = inventory.getProductByCode(productCode);

            if (productToPurchase != null) {
                System.out.print("Ingrese la cantidad que desea comprar: ");
                int purchaseQuantity = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                if (purchaseQuantity > 0) {
                    // Registrar la transacción de compra
                    Transaction purchaseTransaction =
                            new Transaction("T"+String.valueOf(transactionHistory.getCountTransactions()), "compra",
                            "2023-10-08");
                    purchaseTransaction.addTransactionDetail(productCode, purchaseQuantity);
                    transactionHistory.addTransaction(purchaseTransaction);

                    System.out.println("Compra realizada con éxito. Se agregaron " + purchaseQuantity + " unidades al inventario.");
                } else {
                    System.out.println("La cantidad debe ser mayor que cero.");
                }
            } else {
                System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
            }
        }
    }

    public void makeSale() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Ingrese el código del producto que desea vender (o '0' para regresar): ");
            String productCode = scanner.nextLine();

            if (productCode.equals("0")) {
                break; // Regresar al menú principal
            }

            Product productToSell = inventory.getProductByCode(productCode);

            if (productToSell != null) {
                System.out.print("Ingrese la cantidad que desea vender: ");
                int saleQuantity = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                if (saleQuantity > 0 && productToSell.getAmount() >= saleQuantity) {
                    // Registrar la transacción de venta
                    Transaction saleTransaction = new Transaction("T"+String.valueOf(transactionHistory.getCountTransactions()), "venta", "2023-10-08");
                    saleTransaction.addTransactionDetail(productCode, saleQuantity);
                    transactionHistory.addTransaction(saleTransaction);

                    System.out.println("Venta realizada con éxito. Se vendieron " + saleQuantity + " unidades del producto.");
                } else {
                    System.out.println("La cantidad debe ser mayor que cero y no puede superar la cantidad disponible.");
                }
            } else {
                System.out.println("El producto con código " + productCode + " no se encontró en el inventario.");
            }
        }
    }
}
