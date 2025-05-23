import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Kalkulator extends Application {
    @Override
    public void start(Stage primaryStage) {
        //Membuat kotak isian untuk angka pertama dan angka kedua
        TextField angka1 = new TextField();
        angka1.setPromptText("Masukan Angka pertama.");

        TextField angka2 = new TextField();
        angka2.setPromptText("Masukan angka kedua.");

        //Membuat tombol
        Button button = new Button("Tambah");
        //Membuat label
        Label hasilLabel = new Label();

        //Membuat eventlistener
        button.setOnAction(e -> {
            try {
                int a = Integer.parseInt(angka1.getText());
                int b = Integer.parseInt(angka2.getText());
                int hasil = a + b;
                hasilLabel.setText("Hasil: " + hasil);
            } catch (NumberFormatException ex) {
                hasilLabel.setText("mAsukan angka yang benar!");
            }
        });

        //Mengatur layout dengan VBox
        VBox root = new VBox(10, angka1, angka2, button, hasilLabel);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Kalkulator Sederhana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
