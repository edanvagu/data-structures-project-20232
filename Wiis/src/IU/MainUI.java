package IU;
import java.io.IOException;

import InventoryClases.Inventory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainUI extends Application {

    public void start(Stage primaryStage) throws IOException {
        Inventory inventory = new Inventory();
        
        InventoryUI inventoryUI = new InventoryUI(primaryStage, inventory);
        Scene inventoryScene = inventoryUI.createScene();

        TransactionUI transactionUI = new TransactionUI(primaryStage, inventory);
        Scene transactionScene = transactionUI.createScene();

        primaryStage.setTitle("Interfaz de Productos");
        primaryStage.setScene(inventoryScene);
        primaryStage.show();

        // Para cambiar a la escena de transacciones:
        primaryStage.setScene(transactionScene);
    }
}
