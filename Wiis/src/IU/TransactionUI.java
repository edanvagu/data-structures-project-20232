package IU;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import InventoryClases.*;

public class TransactionUI {

    private Stage stage; // El Stage principal
    private TableView<TransactionDetail> table;
    private Transaction currentTransaction;
    private Inventory inventory;
    private TransactionHistory transactionHistory;

    public TransactionUI(Stage stage, Inventory inventory) {
        this.stage = stage;
        this.inventory = inventory;
        this.transactionHistory = new TransactionHistory(inventory);
    }

    public Scene createScene() throws IOException {

        inventory.loadFile();
        transactionHistory.loadFile();

        // Crear botones
        Button buyProductButton = new Button("Comprar producto");
        Button sellProductButton = new Button("Vender producto");

        // Acción para el botón "Comprar producto"
        buyProductButton.setOnAction(e -> showBuyWindow());

        // Acción para el botón "Vender producto"
        sellProductButton.setOnAction(e -> showSellWindow());

        // Crear la tabla de transacciones
        table = new TableView<>();
        TableColumn<TransactionDetail, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<TransactionDetail, String> nameCol = new TableColumn<>("Nombre");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<TransactionDetail, Integer> quantityCol = new TableColumn<>("Cantidad");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<TransactionDetail, String> typeCol = new TableColumn<>("Tipo");
        typeCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTransaction().getType()));

        TableColumn<TransactionDetail, LocalDateTime> dateCol = new TableColumn<>("Fecha");
        dateCol.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getTransaction().getDate()));

        table.getColumns().addAll(idCol, nameCol, quantityCol, typeCol, dateCol);

        // Vincula el ancho de las columnas al ancho de la tabla
        idCol.prefWidthProperty().bind(table.widthProperty().divide(5)); // 25% width
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        quantityCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        typeCol.prefWidthProperty().bind(table.widthProperty().divide(5));
        dateCol.prefWidthProperty().bind(table.widthProperty().divide(5));

        // Organizar los componentes
        HBox topBox = new HBox(10, buyProductButton, sellProductButton);
        VBox root = new VBox(10, topBox, table);
        root.setPadding(new Insets(10));

        SideMenu sideMenu = new SideMenu(stage);

        // Crea un BorderPane y añade el menú lateral y tu vista principal
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sideMenu);
        mainLayout.setCenter(root);

        reloadTransactionHistory();

        Scene scene = new Scene(mainLayout, 1000, 600); // Ajusta el tamaño si es necesario
        return scene;
    }

    // Métodos adicionales para manejar las acciones del usuario
    private void showBuyWindow() {
        Stage buyWindow = new Stage();
        buyWindow.initModality(Modality.APPLICATION_MODAL);
        buyWindow.setTitle("Compra");

        // Inicializar los campos de texto para ingresar el código del producto y la
        // cantidad
        TextField codeField = new TextField();
        codeField.setPromptText("Código del producto");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Cantidad");

        // Etiqueta para mostrar mensajes al usuario
        Label messageLabel = new Label();

        currentTransaction = new Transaction(null, "COMPRA", LocalDateTime.now());

        // Botón para agregar productos a la transacción actual
        Button addButton = new Button("Agregar más productos a la compra");
        addButton.setOnAction(e -> {
            try {
                String code = codeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                // Verificar que el código de producto y la cantidad sean válidos
                if (code.isEmpty() || quantity <= 0) {
                    messageLabel.setText("Código de producto inválido o cantidad no permitida.");
                    return;
                }

                // Crear y agregar un nuevo detalle de transacción a la transacción actual
                currentTransaction.addTransactionDetail(code, quantity);

                // Limpiar los campos de texto para una nueva entrada
                codeField.clear();
                quantityField.clear();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Por favor, ingrese una cantidad válida.");
            }
        });

        // Botón para confirmar la transacción
        Button confirmButton = new Button("Confirmar transacción");
        confirmButton.setOnAction(e -> {
            try {
                String code = codeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                // Verificar que el código de producto y la cantidad sean válidos
                if (!code.isEmpty() && quantity > 0) {
                    // Crear y agregar un nuevo detalle de transacción a la transacción actual
                    TransactionDetail detail = currentTransaction.addTransactionDetail(code, quantity);
                    // Establecer el transactionId en el detalle de la transacción
                    detail.setTransactionId(currentTransaction.getTransactionCode());
                }

                if (currentTransaction.getDetails().isEmpty()) {
                    messageLabel.setText("Agregue al menos un producto antes de confirmar.");
                    return;
                }

                // Añadir la transacción al historial y actualizar la UI
                transactionHistory.addTransaction(currentTransaction);
                messageLabel.setText("Transacción realizada con éxito!");
                transactionHistory.saveFile();

                // Reiniciar la transacción actual para futuras compras
                currentTransaction = null;

                // Actualizar la tabla de la UI con las nuevas transacciones
                reloadTransactionHistory();

                // Cerrar la ventana de compra
                buyWindow.close();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Por favor, ingrese una cantidad válida.");
            }
        });

        // Configurar el layout de la ventana
        VBox layout = new VBox(10, codeField, quantityField, addButton, confirmButton, messageLabel);
        layout.setPadding(new Insets(10));

        // Crear y mostrar la escena
        Scene addTransactionScene = new Scene(layout, 300, 200);
        buyWindow.setScene(addTransactionScene);
        buyWindow.showAndWait();
    }

    private void showSellWindow() {
        Stage sellWindow = new Stage();
        sellWindow.initModality(Modality.APPLICATION_MODAL);
        sellWindow.setTitle("Venta");

        // Initialize the text fields for entering the product code and quantity
        TextField codeField = new TextField();
        codeField.setPromptText("Código del producto");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Cantidad");

        // Label to display messages to the user
        Label messageLabel = new Label();

        currentTransaction = new Transaction(null, "VENTA", LocalDateTime.now());

        // Button to add products to the current transaction
        Button addButton = new Button("Agregar más productos a la venta");
        addButton.setOnAction(e -> {
            try {
                String code = codeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                // Verify that the product code and quantity are valid
                if (code.isEmpty() || quantity <= 0) {
                    messageLabel.setText("Código de producto inválido o cantidad no permitida.");
                    return;
                }

                // Create and add a new transaction detail to the current transaction
                currentTransaction.addTransactionDetail(code, quantity);

                // Clear the text fields for a new entry
                codeField.clear();
                quantityField.clear();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Por favor, ingrese una cantidad válida.");
            }
        });

        // Button to confirm the transaction
        Button confirmButton = new Button("Confirmar transacción");
        confirmButton.setOnAction(e -> {
            try {
                String code = codeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
        
                // Verify that the product code and quantity are valid
                if (!code.isEmpty() && quantity > 0) {
                    // Get the product by its code
                    Product product = inventory.getProductByCode(code);
                    if (product != null) {
                        // Check if the required quantity is available
                        if (product.getQuantity() >= quantity) {
                            // Create and add a new transaction detail to the current transaction
                            TransactionDetail detail = currentTransaction.addTransactionDetail(code, quantity);
                            // Set the transactionId in the transaction detail
                            detail.setTransactionId(currentTransaction.getTransactionCode());
                        } else {
                            messageLabel.setText("No hay suficientes unidades del producto en el inventario.");
                            return;
                        }
                    }
                }
        
                if (currentTransaction.getDetails().isEmpty()) {
                    messageLabel.setText("Agregue al menos un producto antes de confirmar.");
                    return;
                }
        
                // Add the transaction to the history and update the UI
                transactionHistory.addTransaction(currentTransaction);
                messageLabel.setText("Transacción realizada con éxito!");
                transactionHistory.saveFile();
        
                // Reset the current transaction for future sales
                currentTransaction = null;
        
                // Update the UI table with the new transactions
                reloadTransactionHistory();
        
                // Close the sell window
                sellWindow.close();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Por favor, ingrese una cantidad válida.");
            }
        });

        // Set up the window layout
        VBox layout = new VBox(10, codeField, quantityField, addButton, confirmButton, messageLabel);
        layout.setPadding(new Insets(10));

        // Create and display the scene
        Scene addTransactionScene = new Scene(layout, 300, 200);
        sellWindow.setScene(addTransactionScene);
        sellWindow.showAndWait();
    }

    private void reloadTransactionHistory() {
        table.getItems().clear();

        for (Transaction t : transactionHistory.getTransactions()) {
            for (TransactionDetail detail : t.getDetails()) {
                Product product = inventory.getProductByCode(detail.getProductCode());
                if (product != null) {
                    String productName = product.getName();
                    detail.setProductName(productName);
                } else {
                    continue;
                }

                String transactionType = t.getType();
                detail.setTransactionType(transactionType);

                // Set the transaction in each detail
                detail.setTransaction(t);

                table.getItems().add(detail);
            }
        }
    }

}
