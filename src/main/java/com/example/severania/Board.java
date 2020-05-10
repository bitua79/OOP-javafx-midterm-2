package com.example.severania;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean isEnemy = false;
    public int pieceCount = 11;
    public int kingCount = 1;
    public int rookCount = 2;
    public int knightCount = 3;
    public int pawnCount = 5;
    public int width;
    public int height;

    //make board by blocs in columns and rows
    public Board(boolean enemy, EventHandler<? super MouseEvent> eventHandler) {
        this.getStylesheets().add(Main.class.getResource("styles/gameDisplay.css").toExternalForm());
        this.isEnemy = enemy;
        for (int y = 0; y < Controller.size; y++) {
            HBox row = new HBox();
            for (int x = 0; x < Controller.size; x++) {
                Bloc bloc = new Bloc(x, y, this);
                bloc.setOnMouseClicked(eventHandler);
                bloc.getStyleClass().add("rectangle");
                row.getChildren().add(bloc);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    //place a piece Starting from bloc(x, y)
    public boolean placePiece(Piece piece, int x, int y) {
        if (canPlacePiece(piece, x, y)) {
            int width = piece.getWidth();
            int height = piece.getHeight();
            for (int i = x; i < x + width; i++)
                for (int j = y; j < y + height; j++) {
                    Bloc pieceBloc = getBloc(i, j);
                    pieceBloc.setPiece(piece);
                    if (!isEnemy) {
                        pieceBloc.setFill(Color.BISQUE);
                        pieceBloc.setStroke(Color.GREEN);
                    }
                }
            return true;
        }
        return false;
    }

    //return the bloc which locate in "x" th column and "y" th row
    public Bloc getBloc(int x, int y) {
        return (Bloc)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    //return an array which contains all valid neighbors of bloc(x, y)
    public Bloc[] getNeighbors(int x, int y) {
        Point2D[] pointS = new Point2D[]{
                new Point2D(x - 1, y - 1),
                new Point2D(x - 1, y),
                new Point2D(x - 1, y + 1),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1),
                new Point2D(x + 1, y - 1),
                new Point2D(x + 1, y),
                new Point2D(x + 1, y + 1)
        };

        List<Bloc> neighbors = new ArrayList<Bloc>();

        for (Point2D p : pointS) {
            if (isValidPoint(p)) {
                neighbors.add(getBloc((int) p.getX(), (int) p.getY()));
            }
        }

        return neighbors.toArray(new Bloc[0]);
    }

    //return true if we can place the piece starting from bloc(x, y)
    public boolean canPlacePiece(Piece piece, int x, int y) {
       int width = piece.getWidth();
        int height = piece.getHeight();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++){
                //if bloc is not valid
                if (!isValidPoint(i, j))
                    return false;

                Bloc tempBloc = getBloc(i, j);
                //if this bloc belongs to an another piece
                if (tempBloc.getPiece() != null)
                    return false;

                //if bloc was shoot(for buying new pieces)
                if (tempBloc.isDead())
                    return false;

                for (Bloc neighbor : getNeighbors(i, j)) {

                    //if bloc's neighbors belong to a piece
                    if (neighbor.getPiece() != null)
                        return false;
                }
            }
        }
        return true;
    }

    //if this point or actually bloc is valid
    public boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    //if this point or actually bloc is valid by use x and y
    public boolean isValidPoint(double x, double y) {
        return x >= 0 && x < Controller.size && y >= 0 && y < Controller.size;
    }
    public boolean isEnemy() {
        return isEnemy;
    }
}