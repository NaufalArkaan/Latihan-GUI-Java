import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class Codelab extends Application {

    private int angkaTarget;
    private int jumlahPercobaan;

    private Label feedbackLabel;
    private Label percobaanLabel;
    private TextField inputField;
    private Button cobaButton;

    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        // Inisialisasi angka target dan percobaan
        resetGame();

        // Label judul
        Label titleLabel = new Label("Tebak Angka 1-100");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Label feedback (terlalu besar/kecil/benar)
        feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: orange;");

        // TextField input tebakan
        inputField = new TextField();
        inputField.setPromptText("Masukkan angka di sini");
        inputField.setPrefWidth(150);

        // Button coba tebak
        cobaButton = new Button("Coba Tebak!");
        cobaButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // Label jumlah percobaan
        percobaanLabel = new Label("Jumlah percobaan: 0");

        // Event handler button
        cobaButton.setOnAction(e -> {
            String input = inputField.getText();
            if (input.isEmpty()) {
                feedbackLabel.setText("Masukkan angka terlebih dahulu!");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            int tebakan;
            try {
                tebakan = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Input harus berupa angka!");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            jumlahPercobaan++;
            percobaanLabel.setText("Jumlah percobaan: " + jumlahPercobaan);

            if (tebakan < angkaTarget) {
                feedbackLabel.setText("Terlalu kecil!");
                feedbackLabel.setStyle("-fx-text-fill: orange;");
            } else if (tebakan > angkaTarget) {
                feedbackLabel.setText("Terlalu besar!");
                feedbackLabel.setStyle("-fx-text-fill: orange;");
            } else {
                feedbackLabel.setText("Tebakan benar!");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                cobaButton.setText("Main Lagi");
                cobaButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                inputField.setDisable(true);
            }

            // Jika tombol sudah jadi "Main Lagi", reset game saat diklik
            if (cobaButton.getText().equals("Main Lagi")) {
                cobaButton.setOnAction(ev -> resetGame());
            }
        });

        // Layout HBox untuk input dan button
        HBox inputBox = new HBox(10, inputField, cobaButton);
        inputBox.setAlignment(Pos.CENTER);

        // Layout VBox utama
        VBox root = new VBox(10, titleLabel, feedbackLabel, inputBox, percobaanLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #e6f0ff;");

        // Scene dan stage
        Scene scene = new Scene(root, 350, 200);
        primaryStage.setTitle("Tebak Angka");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method untuk reset game
    private void resetGame() {
        angkaTarget = random.nextInt(100) + 1; // angka 1-100
        jumlahPercobaan = 0;

        if (feedbackLabel != null) {
            feedbackLabel.setText("");
        }
        if (percobaanLabel != null) {
            percobaanLabel.setText("Jumlah percobaan: 0");
        }
        if (inputField != null) {
            inputField.clear();
            inputField.setDisable(false);
        }
        if (cobaButton != null) {
            cobaButton.setText("Coba Tebak!");
            cobaButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
            // Reset event handler button ke fungsi utama
            cobaButton.setOnAction(e -> {
                String input = inputField.getText();
                if (input.isEmpty()) {
                    feedbackLabel.setText("Masukkan angka terlebih dahulu!");
                    feedbackLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                int tebakan;
                try {
                    tebakan = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Input harus berupa angka!");
                    feedbackLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                jumlahPercobaan++;
                percobaanLabel.setText("Jumlah percobaan: " + jumlahPercobaan);

                if (tebakan < angkaTarget) {
                    feedbackLabel.setText("Terlalu kecil!");
                    feedbackLabel.setStyle("-fx-text-fill: orange;");
                } else if (tebakan > angkaTarget) {
                    feedbackLabel.setText("Terlalu besar!");
                    feedbackLabel.setStyle("-fx-text-fill: orange;");
                } else {
                    feedbackLabel.setText("Tebakan benar!");
                    feedbackLabel.setStyle("-fx-text-fill: green;");
                    cobaButton.setText("Main Lagi");
                    cobaButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                    inputField.setDisable(true);
                    // Ganti event handler ke reset game
                    cobaButton.setOnAction(ev -> resetGame());
                }
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
