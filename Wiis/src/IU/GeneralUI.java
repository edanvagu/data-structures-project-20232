package IU;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import InventoryClases.Inventory;
import InventoryClases.Product;
import InventoryClases.Transaction;
import InventoryClases.TransactionDetail;
import InventoryClases.TransactionHistory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GeneralUI {
    private Stage stage;
    private SideMenu sideMenu;
    private TransactionHistory transactionHistory;
    private Inventory inventory;

    public GeneralUI(Stage stage, Inventory inventory) {
        this.stage = stage;
        this.sideMenu = new SideMenu(stage);
        this.inventory = inventory;
        this.transactionHistory = new TransactionHistory(inventory);
    }

    public Scene createScene() throws IOException {

        inventory.loadFile();
        transactionHistory.loadFile();

        // Panel principal que contendrá todo el contenido
        GridPane grid = new GridPane();
        // Espaciado entre los elementos del GridPane
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10)); // Margen alrededor del GridPane

        // Estilo para los VBox
        String style = "-fx-background-color: white; -fx-background-radius: 20;";

        VBox totalSalesBox = new VBox();
        totalSalesBox.setSpacing(10); // Espacio entre los elementos internos
        totalSalesBox.setAlignment(Pos.CENTER); // Alineación de los elementos
        totalSalesBox.setStyle(style);

        // Icono de regalo (podrías usar una imagen o un FontAwesome icon si está
        // disponible)
        Label iconLabel = new Label("\uD83C\uDF81"); // Este es un emoji de regalo, como placeholder
        iconLabel.setFont(new Font("Arial", 24)); // Establece el tamaño y tipo de letra del icono

        // Texto descriptivo
        Label titleLabel = new Label("Total en ventas");
        titleLabel.setFont(new Font("Arial", 16)); // Establece el tamaño y tipo de letra del título

        // Monto total de ventas (aquí se deberá recuperar el dato real)
        double totalSalesAmount = getTotalSalesAmount(); // Suponiendo que tienes este método en TransactionHistory
        Label salesAmountLabel = new Label(String.format("$%,.2f", totalSalesAmount));
        salesAmountLabel.setFont(new Font("Arial", 24)); // Establece el tamaño y tipo de letra del monto

        // Agregar los elementos al VBox
        totalSalesBox.getChildren().addAll(iconLabel, titleLabel, salesAmountLabel);

        // Definir el recuadro de productos más vendidos
        VBox topProductsBox = new VBox();
        topProductsBox.setStyle(style); // Aplicar el estilo
        topProductsBox.setPadding(new Insets(10));
        topProductsBox.setAlignment(Pos.CENTER);
        topProductsBox.setSpacing(10);

        // Obtiene los tres productos más vendidos
        
        List<String[]> topProducts = transactionHistory.getTopSoldProducts(3);
        StringBuilder topProductsText = new StringBuilder("Productos más vendidos\n");

        int rank = 1;
        for (String[] productInfo : topProducts) {
            Product product = inventory.getProductByCode(productInfo[0]);
            if (product != null) {
                topProductsText.append(rank).append(". ").append(product.getName())
                        .append(" - Cantidad: ").append(productInfo[1]).append("\n");
                rank++;
            }
        }

        Label topProductsLabel = new Label(topProductsText.toString());
        topProductsLabel.setFont(new Font("Arial", 16));
        topProductsBox.getChildren().add(topProductsLabel);

        // Definir el recuadro de la gráfica de ventas
        VBox chartBox = new VBox();
        chartBox.setStyle(style);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(2);
        xAxis.setForceZeroInRange(false);

        LineChart<Number, Number> salesChart = new LineChart<>(xAxis, new NumberAxis());

        salesChart.setTitle("Ventas del mes");

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the date one month ago
        LocalDate oneMonthAgo = currentDate.minusMonths(1);

        // Initialize a map to store the sales of each day
        Map<LocalDate, Double> salesPerDay = new TreeMap<>();

        // Go through all transactions
        for (Transaction t : transactionHistory.getTransactions()) {
            // For each transaction, if it's a sale and its date is between one month ago
            // and the current date
            LocalDate transactionDate = t.getDate().toLocalDate();
            if (t.getType().equals("VENTA") && !transactionDate.isBefore(oneMonthAgo)
                    && !transactionDate.isAfter(currentDate)) {
                // Add its amount to the corresponding date in the map
                for (TransactionDetail td : t.getDetails()) {
                    Product product = inventory.getProductByCode(td.getProductCode());
                    double amount = td.getQuantity() * product.getPrice();
                    salesPerDay.put(transactionDate, salesPerDay.getOrDefault(transactionDate, 0.0) + amount);
                }
            }
        }

        // Create a data series for the chart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        // Go through the map and add each entry as a data point in the series
        for (Map.Entry<LocalDate, Double> entry : salesPerDay.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().getDayOfMonth(), entry.getValue()));
        }

        // Add the series to the chart
        salesChart.getData().add(series);

        chartBox.getChildren().add(salesChart);

        // Añadir los recuadros de ventas y productos más vendidos a la mitad superior
        HBox topSection = new HBox(10); // Espaciado entre los VBox
        topSection.getChildren().addAll(totalSalesBox, topProductsBox);
        HBox.setHgrow(totalSalesBox, Priority.ALWAYS); // Crecer horizontalmente
        HBox.setHgrow(topProductsBox, Priority.ALWAYS); // Crecer horizontalmente

        // Añadir la sección superior y la gráfica a la cuadrícula
        grid.add(topSection, 0, 0); // La sección superior en la fila 0
        grid.add(chartBox, 0, 1); // La gráfica en la fila 1

        // Configurar las restricciones de crecimiento para la gráfica
        GridPane.setVgrow(chartBox, Priority.ALWAYS);
        GridPane.setHgrow(chartBox, Priority.ALWAYS);

        // Configurar las restricciones de crecimiento para la sección superior
        GridPane.setVgrow(topSection, Priority.ALWAYS);
        GridPane.setHgrow(topSection, Priority.ALWAYS);

        // Configurar el layout principal y añadir el menú lateral
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sideMenu);
        mainLayout.setCenter(grid);

        // Crear y devolver la escena
        Scene scene = new Scene(mainLayout, 1000, 600);
        return scene;
    }

    public double getTotalSalesAmount() {
        double total = 0;
        for (Transaction t : transactionHistory.getTransactions()) {
            for (TransactionDetail td : t.getDetails()) {
                if (t.getType().equals("VENTA")) {
                    Product product = inventory.getProductByCode(td.getProductCode());
                    total += td.getQuantity() * product.getPrice();
                }
            }
        }
        return total;
    }

}