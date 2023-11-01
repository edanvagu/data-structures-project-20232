package IU;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        TableView<Product> table = new TableView<>();
        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Nombre");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Precio");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<Product, Integer> amountCol = new TableColumn<>("Cantidad");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

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

        Button addButton = new Button("Añadir");
        addButton.setOnAction(e -> {
            // Aquí añades el código para procesar los datos ingresados

            // Por ejemplo, guardar en una base de datos o añadir a la tabla

            addProductStage.close(); // Cierra la ventana de añadir producto
        });

        VBox layout = new VBox(10, nameField, priceField, quantityField, addButton);
        layout.setPadding(new Insets(10));

        Scene addProductScene = new Scene(layout, 300, 200);
        addProductStage.setScene(addProductScene);
        addProductStage.showAndWait(); // Espera hasta que esta ventana se cierre
    }

    private ContextMenu createProductContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem updatePriceItem = new MenuItem("Actualizar precio");
        updatePriceItem.setOnAction(e -> {
            // Lógica para actualizar el precio
        });

        MenuItem updateNameItem = new MenuItem("Actualizar nombre");
        updateNameItem.setOnAction(e -> {
            // Lógica para actualizar el nombre
        });

        MenuItem deleteProductItem = new MenuItem("Eliminar producto");
        deleteProductItem.setOnAction(e -> {
            // Lógica para eliminar el producto
        });

        contextMenu.getItems().addAll(updatePriceItem, updateNameItem, deleteProductItem);
        return contextMenu;
    }

    private void addContextMenuToTableRows(TableView<String> table) {
        ContextMenu contextMenu = createProductContextMenu();

        table.setRowFactory(tableView -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) { // Detecta clic derecho
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                } else if (event.getButton() == MouseButton.PRIMARY) { // Detecta clic izquierdo
                    contextMenu.hide();
                }
            });
            return row;
        });
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
            // Aquí coloca la lógica para actualizar el precio
            double newPrice = Double.parseDouble(priceField.getText());
            selectedProduct.setPrice(newPrice);
            modal.close();
        });

        layout.getChildren().addAll(label, priceField, updateButton);

        Scene modalScene = new Scene(layout);
        modal.setScene(modalScene);
        modal.showAndWait();
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
            // Aquí coloca la lógica para actualizar el nombre
            String newName = nameField.getText();
            selectedProduct.setName(newName);
            modal.close();
        });

        layout.getChildren().addAll(label, nameField, updateButton);

        Scene modalScene = new Scene(layout);
        modal.setScene(modalScene);
        modal.showAndWait();
    }

    private void showDeleteProductWindow(Product selectedProduct, ObservableList<Product> productList) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label label = new Label("¿Estás seguro de que deseas eliminar: " + selectedProduct.getName() + "?");
        Button yesButton = new Button("Sí");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            // Aquí coloca la lógica para eliminar el producto
            productList.remove(selectedProduct);
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

}


