package IU;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

import InventoryClases.*;



public class InventoryUI {
    private Stage stage; // El Stage principal
    private Inventory inventory;
    private TableView<Product> table;
    
    public InventoryUI(Stage stage, Inventory inventory) {
        this.stage = stage;
        this.inventory = inventory;
    }    

    public Scene createScene() {
        // Cargar el inventario desde un archivo
        inventory.loadFile();
        

        // Columna 1: Botón Añadir Producto
        Button addProductButton = new Button("+ Añadir Producto");
        HBox firstColumn = new HBox(addProductButton);
        HBox.setHgrow(firstColumn, Priority.NEVER); // Esta columna no crecerá y se quedará a la izquierda

        // Columna 2: Filtro de Precio
        Label priceLabel = new Label("Precio");
        TextField minPriceField = new TextField();
        minPriceField.setPromptText("Mínimo");
        minPriceField.setMaxWidth(80);

        TextField maxPriceField = new TextField();
        maxPriceField.setPromptText("Máximo");
        maxPriceField.setMaxWidth(80);

        Button filterPriceButton = new Button("Filtrar por precio");
        HBox secondColumn = new HBox(10, priceLabel, minPriceField, maxPriceField, filterPriceButton);
        HBox.setHgrow(secondColumn, Priority.ALWAYS); // Esta columna crecerá y empujará a las otras a los extremos

        // Columna 3: Buscar por ID
        TextField searchField = new TextField();
        searchField.setPromptText("Buscar producto por ID");
        searchField.setMaxWidth(200);

        HBox thirdColumn = new HBox(searchField);
        HBox.setHgrow(thirdColumn, Priority.NEVER); // Esta columna no crecerá y se quedará a la derecha

        // Contenedor Principal
        HBox mainContainer = new HBox(firstColumn, secondColumn, thirdColumn);
        mainContainer.setSpacing(10); // Ajusta el espaciado general si es necesario
        mainContainer.setPadding(new Insets(10));
        HBox.setHgrow(secondColumn, Priority.ALWAYS); // Hace que la segunda columna crezca para empujar a las otras

        // Alineación de los elementos dentro de las columnas
        firstColumn.setAlignment(Pos.CENTER_LEFT);
        secondColumn.setAlignment(Pos.CENTER);
        thirdColumn.setAlignment(Pos.CENTER_RIGHT);
        
        // Agrega un listener al campo de texto de búsqueda
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Obtiene el texto actual del campo de búsqueda
            String searchText = newValue;

            // Si el texto de búsqueda no está vacío, busca el producto en el inventario por
            // ID
            if (!searchText.isEmpty()) {
                Product product = inventory.getProductByCode(searchText);

                // Si se encuentra un producto, actualiza la tabla para mostrar solo ese
                // producto
                if (product != null) {
                    table.getItems().clear();
                    table.getItems().add(product);
                } else {
                    // Si no se encuentra un producto, limpia la tabla
                    table.getItems().clear();
                }
            } else {
                // Si el texto de búsqueda está vacío, actualiza la tabla para mostrar todos los
                // productos
                reloadProducts();
            }
        });

        // Acción para el botón "+Añadir Producto"
        addProductButton.setOnAction(e -> showAddProductWindow());
        
        // Acción para el botón "Filtrar por precio"
        filterPriceButton.setOnAction(e -> {
            // Utiliza 0 como valor predeterminado para el precio mínimo si el campo está vacío
            double minPrice = minPriceField.getText().isEmpty() ? 0.0 : Double.parseDouble(minPriceField.getText());
        
            // Utiliza un valor grande como predeterminado para el precio máximo si el campo está vacío
            double maxPrice = maxPriceField.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceField.getText());
        
            filterProductsByPriceRange(minPrice, maxPrice);
        });

        // Crear la tabla de productos
        table = new TableView<>();
        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Nombre");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Precio");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Cantidad");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table.getColumns().addAll(idCol, nameCol, priceCol, quantityCol);

        // Vincula el ancho de las columnas al ancho de la tabla
        idCol.prefWidthProperty().bind(table.widthProperty().divide(4)); // 25% width
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(4));
        quantityCol.prefWidthProperty().bind(table.widthProperty().divide(4));

        // Organizar los componentes
        VBox root = new VBox(10, mainContainer, table);
        root.setPadding(new Insets(10));

        SideMenu sideMenu = new SideMenu(stage);

        // Crea un BorderPane y añade el menú lateral y tu vista principal
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sideMenu);
        mainLayout.setCenter(root);

        addContextMenuToTableRows();

        reloadProducts();

        Scene scene = new Scene(mainLayout, 1000, 600); // Ajusta el tamaño si es necesario
        return scene;
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
                Product product = new Product(productCode, name, price, quantity);
                inventory.addProduct(product);
                messageLabel.setText("Producto añadido con éxito!");
                inventory.saveFile();

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

    private void filterProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> filteredProducts = inventory.getProducts().values().stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    
        table.getItems().clear();
        table.getItems().addAll(filteredProducts);
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

    private void reloadProducts() {
        table.getItems().clear();
        table.getItems().addAll(inventory.getProducts().values());
    }

}