package com.example.severania;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    private ProgressBar progressBar;
    private double progressBarValue;
    private Parent menuRoot0 = null;
    public static AnchorPane menuRoot;
    public static Scene scene;
    public static Image cursor_loading = new Image(Controller.photo + "loadingCursor.gif");
    public static Image cursor_normal = new Image(Controller.photo + "normalCursor.gif");


    @Override
    public void start(Stage primaryStage) throws Exception {

        //set loading page
        AnchorPane loadingRoot = new AnchorPane();
        ImageView loadingPhoto = new ImageView(new Image(Controller.photo + "1_loading.png"));

        loadingPhoto.setFitWidth(1400);
        loadingPhoto.setFitHeight(850);

        progressBar = new ProgressBar();
        progressBar.getStyleClass().add("progressBar");
        progressBar.setPrefWidth(1200);
        progressBar.setLayoutX(70);
        progressBar.setLayoutY(740);
        progressBarValue = 0.01;

        loadingRoot.getChildren().addAll(loadingPhoto, progressBar);
        loadingRoot.getStylesheets().add(Main.class.getResource("styles/loading.css").toExternalForm());
        Font.loadFont("file:src/main/resources/com/example/severania/font/ACERECORDS.TTF", 20);
        loadingRoot.setCursor(new ImageCursor(cursor_loading));

        Controller.menuMediaPlayer.play();
        Controller.menuMediaPlayer.setCycleCount(1500);
        scene = new Scene(loadingRoot, 1350, 790);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Controller.photo + "icon.png"));
        primaryStage.show();

        //spend a time for progress
        Timeline progressTimeline = new Timeline(new KeyFrame(Duration.millis(30), ae -> {
            progressBarValue *= 1.02;
            progressBar.setProgress(progressBarValue);
        }));

        //after complete progressBar change the root to MenuPage
        progressTimeline.setOnFinished(event -> {
            try {
                menuRoot0 = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (menuRoot0 != null)
                menuRoot0.setOpacity(0.1);

            //set a pane to handle fading the root
            menuRoot = new AnchorPane();
            Rectangle rectangle = new Rectangle(1350, 800);
            rectangle.setFill(Paint.valueOf("#121524"));
            menuRoot.getChildren().addAll(rectangle, menuRoot0);
            menuRoot.setCursor(new ImageCursor(cursor_normal));

            primaryStage.getScene().setRoot(menuRoot);

            Timeline menuOpenTimeline = new Timeline(new KeyFrame(Duration.millis(120), ae -> {
                menuRoot0.setOpacity(menuRoot0.getOpacity() + 0.05);
            }));
            menuOpenTimeline.setCycleCount(20);
            menuOpenTimeline.play();
        });
        progressTimeline.setCycleCount(233);
        progressTimeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
