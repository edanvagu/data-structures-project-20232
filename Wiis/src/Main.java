import InventoryClases.Inventory;
import InventoryClases.TransactionHistory;

public class Main {
    public static void main(String[] args) throws Exception {

        int cantidad = 10000000;
        Inventory inventory = new Inventory();
        inventory.loadFile();
        TransactionHistory TrH = new TransactionHistory(inventory);
        long tiempoInicial = System.nanoTime();
        TrH.loadFile(cantidad);
        long tiempoFinal = System.nanoTime();

        System.out.println("Cantidad de datos = " + cantidad + " y tiempo addTransaction = "+ (tiempoFinal - tiempoInicial));
        // MEDIR addProduct
//        inventory.loadFile(cantidad); //este método carga el archivo de productos y lo guarda en el hashmap y en el treemap
//
//        //MEDIR findProduct
//        long tiempoInicial = System.nanoTime();
//        for (int i = 0; i <cantidad; i++) {
//            inventory.searchProduct("P"+i);
//        }
//        long tiempoFinal = System.nanoTime();
//
//        System.out.println("Cantidad de datos = " + cantidad + " y tiempo findProduct = "+ (tiempoFinal - tiempoInicial));
//        tiempoInicial = 0;
//        tiempoFinal = 0;
//        //MEDIR updateProduct
//        tiempoInicial = System.nanoTime();
//        for (int i = 0; i <cantidad; i++) {
//            inventory.updateProduct("P"+i);
//        }
//        tiempoFinal = System.nanoTime();
//        System.out.println("Cantidad de datos = " + cantidad + " y tiempo updateProduct = "+ (tiempoFinal - tiempoInicial));
//        tiempoInicial = 0;
//        tiempoFinal = 0;
//        //MEDIR deleteProduct
//        tiempoInicial = System.nanoTime();
//        for (int i = 0; i <cantidad; i++) {
//            inventory.removeProduct("P"+i);
//        }
//        tiempoFinal = System.nanoTime();
//        System.out.println("Cantidad de datos = " + cantidad + " y tiempo deleteProduct = "+ (tiempoFinal - tiempoInicial));
//        tiempoInicial = 0;
//        tiempoFinal = 0;
    }
}