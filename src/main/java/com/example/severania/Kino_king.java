package com.example.severania;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Kino_king {
    public Button button;
    public int playerCounter;
    public int enemyCounter;
    private final int period;
    private final int powerHeight;
    private final int powerWidth;

    public Kino_king() {
        this.button = new Button("Kino");
        ImageView icon = new ImageView(new Image(Controller.photo+"king.png"));
        icon.setFitWidth(37);
        icon.setFitHeight(37);
        this.button.setGraphic(icon);

        this.button.getStyleClass().add("buttonOnPane");
        this.button.setPrefWidth(120);
        this.button.setPrefHeight(65);
        this.button.setLayoutX(185);
        this.button.setLayoutY(120);

        this.playerCounter = 0;
        this.enemyCounter = 1;
        this.period = 15;
        this.powerHeight = 4;
        this.powerWidth = 2;

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
