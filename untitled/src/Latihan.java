import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.effect.Glow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.*;

public class Latihan extends Application {
    private int targetNumber;
    private int attempts = 0;
    private int score = 0;
    private int difficultyRange = 100;
    private boolean gameOver = false;
    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;
    private MediaPlayer backgroundMusic;
    private Label feedbackLabel;
    private Label scoreLabel;
    private Label attemptsLabel;
    private Label highScoreLabel;
    private int highScore = 0;
    private Timeline timer;
    private int timeLeft = 30;
    private Label timerLabel;
    private TextField inputField;
    private Button guessBtn;

    @Override
    public void start(Stage primaryStage) {
        loadHighScore();
        setupGame(primaryStage);
    }

    private void setupGame(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 20;");

        HBox difficultyBox = new HBox(10);
        Button easyBtn = new Button("Easy (1-50)");
        Button mediumBtn = new Button("Medium (1-100)");
        Button hardBtn = new Button("Hard (1-200)");

        easyBtn.setOnAction(e -> startGame(primaryStage, 50));
        mediumBtn.setOnAction(e -> startGame(primaryStage, 100));
        hardBtn.setOnAction(e -> startGame(primaryStage, 200));

        difficultyBox.getChildren().addAll(easyBtn, mediumBtn, hardBtn);
        root.getChildren().add(new Label("🎮 Pilih Tingkat Kesulitan:"));
        root.getChildren().add(difficultyBox);

        VBox gameUI = createGameUI();
        gameUI.setVisible(false);
        root.getChildren().add(gameUI);

        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setTitle("🎮 Tebak Angka Pro Max");
        primaryStage.show();
    }

    private VBox createGameUI() {
        VBox gameBox = new VBox(15);
        gameBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("🎯 Tebak Angka 1-" + difficultyRange);
        titleLabel.setFont(Font.font(24));
        titleLabel.setTextFill(Color.DARKBLUE);

        feedbackLabel = new Label("");
        feedbackLabel.setFont(Font.font(18));

        inputField = new TextField();
        inputField.setPromptText("Masukkan angka (1-" + difficultyRange + ")");
        inputField.setPrefWidth(200);

        guessBtn = new Button("Coba Tebak!");
        guessBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");

        Glow glow = new Glow(0.8);
        guessBtn.setOnMouseEntered(e -> guessBtn.setEffect(glow));
        guessBtn.setOnMouseExited(e -> guessBtn.setEffect(null));

        HBox infoBox = new HBox(20);
        scoreLabel = new Label("Skor: 0");
        attemptsLabel = new Label("Percobaan: 0");
        highScoreLabel = new Label("High Score: " + highScore);
        timerLabel = new Label("Waktu: 30s");
        infoBox.getChildren().addAll(scoreLabel, attemptsLabel, highScoreLabel, timerLabel);
        infoBox.setAlignment(Pos.CENTER);

        guessBtn.setOnAction(e -> handleGuess());

        gameBox.getChildren().addAll(titleLabel, feedbackLabel, inputField, guessBtn, infoBox);
        return gameBox;
    }

    private void startGame(Stage primaryStage, int range) {
        this.difficultyRange = range;
        this.targetNumber = new Random().nextInt(range) + 1;
        this.attempts = 0;
        this.score = 0;
        this.gameOver = false;
        this.timeLeft = 30;

        VBox root = (VBox) primaryStage.getScene().getRoot();
        VBox gameUI = (VBox) root.getChildren().get(2);
        gameUI.setVisible(true);

        scoreLabel.setText("Skor: 0");
        attemptsLabel.setText("Percobaan: 0");
        highScoreLabel.setText("High Score: " + highScore);
        timerLabel.setText("Waktu: 30s");
        feedbackLabel.setText("");
        feedbackLabel.setTextFill(Color.BLACK);
        inputField.setDisable(false);
        inputField.clear();
        guessBtn.setText("Coba Tebak!");
        guessBtn.setDisable(false);
        guessBtn.setOnAction(e -> handleGuess());

        if (timer != null) {
            timer.stop();
        }
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Waktu: " + timeLeft + "s");
            if (timeLeft <= 0) {
                timer.stop();
                gameOver = true;
                feedbackLabel.setText("⏰ Waktu habis! Coba lagi ya!");
                feedbackLabel.setTextFill(Color.RED);
                guessBtn.setText("Main Lagi");
                guessBtn.setOnAction(ev -> startGame(primaryStage, difficultyRange));
                inputField.setDisable(true);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void handleGuess() {
        if (gameOver) return;

        String input = inputField.getText();
        if (input.isEmpty()) {
            setFeedback("⚠ Masukkan angka terlebih dahulu!", Color.RED);
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            setFeedback("⚠ Input harus berupa angka!", Color.RED);
            return;
        }

        if (guess < 1 || guess > difficultyRange) {
            setFeedback("⚠ Angka harus antara 1 sampai " + difficultyRange + "!", Color.RED);
            return;
        }

        attempts++;
        attemptsLabel.setText("Percobaan: " + attempts);

        if (guess < targetNumber) {
            setFeedback("⬇ Terlalu kecil!", Color.ORANGE);
            playWrongSound();
        } else if (guess > targetNumber) {
            setFeedback("⬆ Terlalu besar!", Color.ORANGE);
            playWrongSound();
        } else {
            setFeedback("✅ Tebakan benar!", Color.GREEN);
            playCorrectSound();
            score += 10;
            scoreLabel.setText("Skor: " + score);
            if (score > highScore) {
                highScore = score;
                highScoreLabel.setText("High Score: " + highScore);
                saveHighScore();
            }
            gameOver = true;
            timer.stop();
            inputField.setDisable(true);
            guessBtn.setText("Main Lagi");
            guessBtn.setOnAction(e -> startGame((Stage) guessBtn.getScene().getWindow(), difficultyRange));
        }
        inputField.clear();
    }

    private void setFeedback(String message, Color color) {
        feedbackLabel.setText(message);
        feedbackLabel.setTextFill(color);
        // Animasi sederhana
        FadeTransition ft = new FadeTransition(Duration.millis(500), feedbackLabel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void playCorrectSound() {
        // Implementasi suara benar (bisa pakai file .mp3 lokal)
        // Contoh: correctSound.play();
    }

    private void playWrongSound() {
        // Implementasi suara salah (bisa pakai file .mp3 lokal)
        // Contoh: wrongSound.play();
    }

    private void loadHighScore() {
        try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {
            highScore = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            highScore = 0;
        }
    }

    private void saveHighScore() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("highscore.txt"))) {
            bw.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}