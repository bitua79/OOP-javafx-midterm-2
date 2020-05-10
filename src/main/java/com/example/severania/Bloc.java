package com.example.severania;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Bloc extends Rectangle{

    private int x, y;
    private Piece piece = null;
    private boolean dead = false;
    private Board board;

    //sounds
    private AudioClip destroyBloc = new AudioClip("file:src/main/resources/com/example/severania/media//bloc.wav");
    private AudioClip hit = new AudioClip("file:src/main/resources/com/example/severania/media//knife.wav");
    private AudioClip kill = new AudioClip("file:src/main/resources/com/example/severania/media//kill.wav");


    public Bloc(int x, int y, Board board) {
        super((int)(400/Controller.size), (int)(400/Controller.size));
        this.x = x;
        this.y = y;
        this.board = board;
        setOnMouseEntered(event -> {
            if (!isDead()){
                if (board.isEnemy() || (!board.isEnemy() && piece==null))
                    setFill(Color.WHITE);
            }
        });
        setOnMouseExited(event -> {
            if (!isDead()){
                if (board.isEnemy() || (!board.isEnemy() && piece==null))
                setFill(Color.BLACK);
            }
        });
    }

    //it set the died cells and return if this cell is a ship cell or not
    public boolean shoot() {
        dead = true;
        if(piece == null){
            setFill(new ImagePattern(new Image(Controller.photo+"dead.jpg")));
            setOpacity(0.7);
            if (board.isEnemy()){
                destroyBloc.play();
            }
        }

        if (piece != null ) {
            piece.hit();
            hit.play();
            Image image = new Image(Controller.photo+"tenor.gif");
            setFill(new ImagePattern(image));
//            Bloc[] neighbors = board.getNeighbors(x, y);


            //after shoot all blocs of a piece
            if (!piece.isAlive()) {
                kill.play();
                //player gets some money after shoot to enemy piece
                if(piece.isEnemy()) {
                    if (piece.getType() == 11){
                        Controller.playerMoney += 1000;
                        board.kingCount--;
                    }
                    else if (piece.getType() == 10 || piece.getType() == 9) {
                        Controller.playerMoney += 500;
                        board.rookCount--;
                    }
                    else if (piece.getType() > 5 && piece.getType() <= 8) {
                        Controller.playerMoney += 300;
                        board.knightCount--;
                    }
                    else if (piece.getType() > 0 && piece.getType() <= 5) {
                        Controller.playerMoney += 100;
                        board.pawnCount--;
                    }
                }
                else if(!piece.isEnemy()){
                    if (piece.getType() == 11){
                        board.kingCount--;
                    }
                    else if (piece.getType() == 10 || piece.getType() == 9) {
                        board.rookCount--;
                    }
                    else if (piece.getType() > 5 && piece.getType() <= 8) {
                        board.knightCount--;
                    }
                    else if (piece.getType() > 0 && piece.getType() <= 5) {
                        board.pawnCount--;
                    }
                }
                board.pieceCount--;
            }
            return true;
        }
        return false;
    }

    //getter and setter
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getBlocX() {
        return x;
    }
    public void setBlocX(int x) {
        this.x = x;
    }

    public int getBlocY() {
        return y;
    }
    public void setBlocY(int y) {
        this.y = y;
    }

    public Board getBoard() {
        return board;
    }

}

