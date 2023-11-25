package IU;

import java.io.IOException;

import InventoryClases.Inventory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SideMenu extends VBox {
    private Stage stage; // El Stage principal
    private Inventory inventory;

    public SideMenu(Stage stage) {
        this.stage = stage;
        this.inventory = new Inventory();
        setSpacing(10);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Label title = new Label("Wiis");
        title.setFont(new Font(24));
        title.setTextFill(Color.WHITE);

        Button generalBtn = new Button("General");
        Button inventoryBtn = new Button("Inventario");
        inventoryBtn.setOnAction(e -> {
            InventoryUI inventoryUI = new InventoryUI(stage, inventory);
            Scene inventoryScene = inventoryUI.createScene();
            this.stage.setScene(inventoryScene);
        });

        Button transactionsBtn = new Button("Transacciones");
        transactionsBtn.setOnAction(e -> {
            TransactionUI transactionUI = new TransactionUI(stage, inventory);
            Scene transactionScene = null;
            try {
                transactionScene = transactionUI.createScene();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (transactionScene != null) {
                this.stage.setScene(transactionScene);
            }
        });

        Button settingsBtn = new Button("Configuración");

        this.getChildren().addAll(title, generalBtn, inventoryBtn, transactionsBtn, settingsBtn);
    }
}
