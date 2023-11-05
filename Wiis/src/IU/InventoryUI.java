package IU;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import InventoryClases.*;



public class InventoryUI extends Application {

    private final Inventory inventory = new Inventory();
    private TableView<Product> table;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Interfaz de Productos");

        // Crear botón y campo de texto
        Button addProductButton = new Button("+ Añadir Producto");
        TextField searchField = new TextField();
        searchField.setPromptText("Producto por ID");

        // Acción para el botón "+Añadir Producto"
        addProductButton.setOnAction(e -> showAddProductWindow());

        // Crear la tabla de productos
        table = new TableView<>();
        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Nombre");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Precio");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> amountCol = new TableColumn<>("Cantidad");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        table.getColumns().addAll(idCol, nameCol, priceCol, amountCol);

        // Vincula el ancho de las columnas al ancho de la tabla
        idCol.prefWidthProperty().bind(table.widthProperty().divide(4)); // 25% width
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        amountCol.prefWidthProperty().bind(table.widthProperty().divide(4));

        // Organizar los componentes
        HBox topBox = new HBox(10, addProductButton, searchField);
        VBox root = new VBox(10, topBox, table);
        root.setPadding(new Insets(10));



        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        SideMenu sideMenu = new SideMenu();

        // Crea un BorderPane y añade el menú lateral y tu vista principal
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sideMenu);
        mainLayout.setCenter(root);

        addContextMenuToTableRows();

        reloadProducts();

        Scene scene1 = new Scene(mainLayout, 1000, 600); // Ajusta el tamaño si es necesario
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    private void showAddProductWindow() {
        Stage addProductStage = new Stage();
        addProductStage.setTitle("Añadir Producto");
        addProductStage.initModality(Modality.WINDOW_MODAL); // Hace que la ventana principal no sea accesible mientras esta ventana esté abierta

        // Crear campos para Nombre, Precio y Cantidad
        TextField nameField = new TextField();
        nameField.setPromptText("Nombre del producto");

        TextField priceField = new TextField();
        priceField.setPromptText("Precio del producto");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Cantidad del producto");

        Label messageLabel = new Label(); // Etiqueta para mostrar mensajes al usuario

        Button addButton = new Button("Añadir");
        addButton.setOnAction(e -> {
            try {
                // Aquí añades el código para procesar los datos ingresados
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String productCode = "P" + String.valueOf(inventory.getCountProducts());
                Product product = new Product(productCode,name, price, quantity);
                inventory.addProduct(product);
                messageLabel.setText("Producto añadido con éxito!");

                reloadProducts();

                addProductStage.close(); // Cierra la ventana de añadir producto
            } catch (NumberFormatException ex) {
                messageLabel.setText("Por favor, introduce un número válido para el precio y la cantidad.");
            } catch (Exception ex) {
                messageLabel.setText("Ocurrió un error al añadir el producto. Por favor, inténtalo de nuevo.");
            }
        });

        VBox layout = new VBox(10, nameField, priceField, quantityField, addButton, messageLabel);
        layout.setPadding(new Insets(10));

        Scene addProductScene = new Scene(layout, 300, 200);
        addProductStage.setScene(addProductScene);
        addProductStage.showAndWait(); // Espera hasta que esta ventana se cierre
    }


    private void showUpdateNameWindow(Product selectedProduct) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label label = new Label("Actualizar nombre para: " + selectedProduct.getName());
        TextField nameField = new TextField();
        nameField.setPromptText("Nuevo nombre");
        Button updateButton = new Button("Actualizar");

        updateButton.setOnAction(e -> {
            String newName = nameField.getText();
            inventory.updateProductName(selectedProduct.getCode(), newName); // Utiliza el método de actualización de Inventory
            reloadProducts(); // Recarga los productos en la tabla
            modal.close();
        });

        layout.getChildren().addAll(label, nameField, updateButton);

        Scene modalScene = new Scene(layout);
        modal.setScene(modalScene);
        modal.showAndWait();
    }

    private void showUpdatePriceWindow(Product selectedProduct) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label label = new Label("Actualizar precio para: " + selectedProduct.getName());
        TextField priceField = new TextField();
        priceField.setPromptText("Nuevo precio");
        Button updateButton = new Button("Actualizar");

        updateButton.setOnAction(e -> {
            try {
                double newPrice = Double.parseDouble(priceField.getText());
                inventory.updateProductPrice(selectedProduct.getCode(), newPrice); // Utiliza el método de actualización de Inventory
                reloadProducts(); // Recarga los productos en la tabla
                modal.close();
            } catch (NumberFormatException ex) {
                // Maneja el caso en que el texto ingresado no sea un número válido
            }
        });

        layout.getChildren().addAll(label, priceField, updateButton);

        Scene modalScene = new Scene(layout);
        modal.setScene(modalScene);
        modal.showAndWait();
    }

    private void showDeleteProductWindow(Product selectedProduct) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label label = new Label("¿Estás seguro de que deseas eliminar: " + selectedProduct.getName() + "?");
        Button yesButton = new Button("Sí");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            inventory.removeProduct(selectedProduct.getCode()); // Utiliza el método de eliminación de Inventory
            reloadProducts(); // Recarga los productos en la tabla
            modal.close();
        });

        noButton.setOnAction(e -> {
            modal.close();
        });

        layout.getChildren().addAll(label, yesButton, noButton);

        Scene modalScene = new Scene(layout);
        modal.setScene(modalScene);
        modal.showAndWait();
    }

    private ContextMenu createProductContextMenu(Product selectedProduct) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem updatePriceItem = new MenuItem("Actualizar precio");
        updatePriceItem.setOnAction(e -> {
            if (selectedProduct != null) {
                showUpdatePriceWindow(selectedProduct);
            }
        });

        MenuItem updateNameItem = new MenuItem("Actualizar nombre");
        updateNameItem.setOnAction(e -> {
            if (selectedProduct != null) {
                showUpdateNameWindow(selectedProduct);
            }
        });

        MenuItem deleteProductItem = new MenuItem("Eliminar producto");
        deleteProductItem.setOnAction(e -> {
            if (selectedProduct != null) {
                showDeleteProductWindow(selectedProduct);
            }
        });

        contextMenu.getItems().addAll(updatePriceItem, updateNameItem, deleteProductItem);
        return contextMenu;
    }

    private void addContextMenuToTableRows() {
        table.setRowFactory(tableView -> {
            TableRow<Product> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Product selectedProduct = row.getItem();
                    if (selectedProduct != null) {
                        if (event.getButton() == MouseButton.SECONDARY) { // Detecta clic derecho
                            ContextMenu contextMenu = createProductContextMenu(selectedProduct); // Crea el menú contextual con el producto seleccionado
                            contextMenu.show(row, event.getScreenX(), event.getScreenY());
                        } else if (event.getButton() == MouseButton.PRIMARY && row.getContextMenu() != null) { // Detecta clic izquierdo
                            row.getContextMenu().hide();
                        }
                    }
                }
            });
            return row;
        });
    }


    private void reloadProducts(){
        table.getItems().clear();
        table.getItems().addAll(inventory.getProducts());
    }

}