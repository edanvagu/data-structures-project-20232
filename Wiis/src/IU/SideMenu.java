package IU;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class SideMenu extends VBox {
    public SideMenu() {
        setSpacing(10);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Label title = new Label("Wiis");
        title.setFont(new Font(24));
        title.setTextFill(Color.WHITE);

        Button generalBtn = new Button("General");
        Button mensajesBtn = new Button("Mensajes");
        // Puedes añadir un pequeño círculo rojo con el número de mensajes no leídos.
        Circle unreadMsgCircle = new Circle(5, Color.RED);
        Label unreadMsgLabel = new Label("2");
        unreadMsgLabel.setTextFill(Color.RED);
        HBox mensajesBox = new HBox(5, mensajesBtn, unreadMsgCircle, unreadMsgLabel);

        Button productosBtn = new Button("Productos");
        Button inventarioBtn = new Button("Inventario");
        Button transaccionesBtn = new Button("Transacciones");
        Button otrosBtn = new Button("Otros");
        Button configuracionBtn = new Button("Configuración");

        this.getChildren().addAll(title, generalBtn, mensajesBox, productosBtn, inventarioBtn, transaccionesBtn, otrosBtn, configuracionBtn);
    }
}

