import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Label utama dengan styling
        Label titleLabel = new Label("Selamat Datang di Aplikasi JavaFX!");
        titleLabel.setFont(Font.font("Arial Black", 28));
        titleLabel.setTextFill(Color.DARKSLATEBLUE);
        titleLabel.setEffect(new DropShadow(5, Color.LIGHTGRAY));

        // Label status yang akan berubah
        Label statusLabel = new Label("Tekan tombol di bawah ini.");
        statusLabel.setFont(Font.font("Verdana", 16));
        statusLabel.setTextFill(Color.DIMGRAY);

        // Tombol dengan efek animasi
        Button actionButton = new Button("Klik Saya!");
        actionButton.setFont(Font.font("Tahoma", 18));
        actionButton.setStyle("-fx-background-color: linear-gradient(to right, #6a11cb, #2575fc); -fx-text-fill: white; -fx-background-radius: 15;");
        actionButton.setPadding(new Insets(10, 20, 10, 20));
        actionButton.setEffect(new DropShadow(10, Color.web("#2575fc")));

        // Animasi scale saat hover tombol
        ScaleTransition st = new ScaleTransition(Duration.millis(200), actionButton);
        actionButton.setOnMouseEntered(e -> {
            st.setToX(1.1);
            st.setToY(1.1);
            st.playFromStart();
        });
        actionButton.setOnMouseExited(e -> {
            st.setToX(1.0);
            st.setToY(1.0);
            st.playFromStart();
        });

        // Aksi tombol mengubah teks label
        actionButton.setOnAction(e -> {
            statusLabel.setText("Tombol sudah diklik! 🎉");
            statusLabel.setTextFill(Color.FORESTGREEN);
        });

        // Layout tombol dalam HBox agar center
        HBox buttonBox = new HBox(actionButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // Layout utama VBox
        VBox root = new VBox(15, titleLabel, statusLabel, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f4ff, #d9e4ff);");

        // Scene dan stage
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("Aplikasi JavaFX Stylish");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//public class Main extends Application {
//    Label label = new Label("Nama:");
//    TextField textfield = new TextField();
//
//    public static void main(String[] args) {
//
//    }
//}
