import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Latihan extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("VBox Experiment 1");

        Label label = new Label("Ini adalah label di dalam VBox");

        Button button1 = new Button("Button Number 1");
        Button button2 = new Button("Button Number 2");

        HBox hbox = new HBox(20, button1, button2);
        VBox vbox = new VBox(30, label, hbox);

        vbox.setStyle("-fx-padding: 20px;");
        Scene scene = new Scene(vbox, 700, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}