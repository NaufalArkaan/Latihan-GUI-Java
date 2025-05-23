import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Praktik extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Label label = new Label("Nama"); //label untuk menampilkan text

        TextField textfield = new TextField(); //Ini adalah kotak untuk pengguna mengetik nama.

        Button button = new Button("Kirim"); //ini adalah tombol yang akan diklik pengguna.

        //Buat Eventlistener Baru
        button.setOnAction(e -> {
            String nama = textfield.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sapaan");
            alert.setHeaderText(null);
            alert.setContentText("Hallo, " + nama + "!");
            alert.showAndWait();
        });

        VBox root= new VBox(10, label, textfield, button); //Ini adalah Vbox untuk menyusun ketiga komponen secara vertikal.

        Scene scene = new Scene(root, 350, 180); //Ini akan memasukan layout vbox sebelumnya (yang namanya ʻrootʼ) tadi ke dalam scene dengan size 350x180

        primaryStage.setScene(scene); //Ini memasukan scene tadi ke stage kita (primaryStage) pada argumen “start()”, kemudian kasih title stagenya, kemudian tampilkan.
        primaryStage.setTitle("Contoh javafx sederhana");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
