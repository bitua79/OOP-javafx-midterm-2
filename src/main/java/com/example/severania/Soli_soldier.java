package com.example.severania;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Soli_soldier {
    public Button button;
    public int playerCounter;
    public int enemyCounter;
    private final int period;
    private final int powerHeight;
    private final int powerWidth;

    public Soli_soldier() {
        this.button = new Button("Soli");
        ImageView icon = new ImageView(new Image(Controller.photo+"soldier.png"));
        icon.setFitWidth(35);
        icon.setFitHeight(40);
        this.button.setGraphic(icon);

        this.button.getStyleClass().add("buttonOnPane");
        this.button.setPrefWidth(120);
        this.button.setPrefHeight(65);
        this.button.setLayoutX(50);
        this.button.setLayoutY(45);

        this.playerCounter = 0;
        this.enemyCounter = 1;
        this.period = 1;
        this.powerHeight = 1;
        this.powerWidth = 1;

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
