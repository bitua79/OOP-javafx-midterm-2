package com.example.severania;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Rooki_rook {
    public Button button;
    public int playerCounter;
    public int enemyCounter;
    private final int period;
    private final int powerHeight;
    private final int powerWidth;

    public Rooki_rook() {
        this.button = new Button("Rooki");
        ImageView icon = new ImageView(new Image(Controller.photo+"rook.png"));
        icon.setFitWidth(33);
        icon.setFitHeight(30);
        this.button.setGraphic(icon);

        this.button.getStyleClass().add("buttonOnPane");
        this.button.setPrefWidth(120);
        this.button.setPrefHeight(65);
        this.button.setLayoutX(55);
        this.button.setLayoutY(120);

        this.playerCounter = 0;
        this.enemyCounter = 1;
        this.period = 10;
        this.powerHeight = 2;
        this.powerWidth = 3;

    }

    public int getPeriod() {
        return period;
    }

    public int getPowerHeight() {
        return powerHeight;
    }

    public int getPowerWidth() {
        return powerWidth;
    }
}
