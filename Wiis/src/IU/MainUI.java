package IU;
import java.io.IOException;

import InventoryClases.Inventory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainUI extends Application {

    public void start(Stage primaryStage) throws IOException {
        Inventory inventory = new Inventory();
        
        GeneralUI generalUI = new GeneralUI(primaryStage, inventory);
        Scene generalScene = generalUI.createScene();

        primaryStage.setTitle("Interfaz de Productos");
        primaryStage.setScene(generalScene);
        primaryStage.show();
    }
}
