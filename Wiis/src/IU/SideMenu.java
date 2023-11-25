package IU;

import java.io.IOException;

import InventoryClases.Inventory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
    private Button generalBtn;
    private Button inventoryBtn;
    private Button transactionsBtn;
    private Button selectedButton; // El botón seleccionado
    private String selectedButtonStyle; // El estilo del botón seleccionado

    public SideMenu(Stage stage) {
        this.stage = stage;
        this.inventory = new Inventory();
        setSpacing(10);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))); // Fondo negro

        Label title = new Label("Wiis");
        title.setFont(new Font("Arial", 24)); // Cambia el tipo de fuente si tienes uno específico en mente
        title.setTextFill(Color.WHITE);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER_LEFT);
        title.setPadding(new Insets(20, 15, 20, 15)); // Ajusta el relleno según necesites

        // Estilo común para todos los botones
        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: white;"
                + "-fx-font-size: 16px; -fx-alignment: center-left; -fx-padding: 10 15 10 15;";

        generalBtn = new Button("General");
        generalBtn.setStyle(buttonStyle);
        generalBtn.setMaxWidth(Double.MAX_VALUE);
        generalBtn.setOnAction(e -> {
            GeneralUI generalUI = new GeneralUI(stage, inventory);
            Scene generalScene = null;
            try {
                generalScene = generalUI.createScene();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (generalScene != null) {
                this.stage.setScene(generalScene);
            }
        });

        inventoryBtn = new Button("Inventario");
        inventoryBtn.setStyle(buttonStyle);
        inventoryBtn.setMaxWidth(Double.MAX_VALUE);
        inventoryBtn.setOnAction(e -> {
            InventoryUI inventoryUI = new InventoryUI(stage, inventory);
            Scene inventoryScene = inventoryUI.createScene();
            this.stage.setScene(inventoryScene);
        });

        transactionsBtn = new Button("Transacciones");
        transactionsBtn.setStyle(buttonStyle);
        transactionsBtn.setMaxWidth(Double.MAX_VALUE);
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

        selectButton(null); // Asegúrate de que ningún botón esté seleccionado al inicio

        this.getChildren().addAll(title, generalBtn, inventoryBtn, transactionsBtn);
    }

    private void selectButton(Button button) {
        // Restablecer el estilo del botón previamente seleccionado
        if (selectedButton != null) {
            selectedButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"
                    + "-fx-font-size: 16px; -fx-alignment: center-left; -fx-padding: 10 15 10 15;");
        }

        // Aplicar estilo al botón seleccionado
        if (button != null) {
            button.setStyle(selectedButtonStyle);
        }

        // Actualizar el botón seleccionado
        selectedButton = button;
    }

}
