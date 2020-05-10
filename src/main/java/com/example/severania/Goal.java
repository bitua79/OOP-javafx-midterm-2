package com.example.severania;

public class Goal extends Bloc{
    private Bloc right;
    private Bloc left;
    private Bloc up;
    private Bloc down;
    private Bloc right_up;
    private Bloc right_down;
    private Bloc left_up;
    private Bloc left_down;


    public Goal(int x, int y, Board board) {
        super(x, y, board);
        if (board.isValidPoint(x+1, y))
            this.down = board.getBloc(x+1, y);

        if (board.isValidPoint(x+1, y+1))
            this.right_down =board.getBloc(x+1, y+1);

        if (board.isValidPoint(x+1, y))
            this.left_down = board.getBloc(x+1, y);

        if (board.isValidPoint(x-1, y))
            this.up = board.getBloc(x-1, y);

        if (board.isValidPoint(x-1, y+1))
            this.right_up = board.getBloc(x-1, y+1);

        if (board.isValidPoint(x-1, y-1))
            this.left_up = board.getBloc(x-1, y-1);

        if (board.isValidPoint(x, y+1))
            this.right = board.getBloc(x, y+1);

        if (board.isValidPoint(x, y-1))
            this.left = board.getBloc(x, y-1);
    }

    //getter and setter
    public Bloc getRight() {
        return right;
    }

    public Bloc getLeft() {
        return left;
    }

    public Bloc getUp() {
        return up;
    }

    public Bloc getDown() {
        return down;
    }

    public Bloc getRight_up() {
        return right_up;
    }

    public Bloc getRight_down() {
        return right_down;
    }

    public Bloc getLeft_up() {
        return left_up;
    }

    public Bloc getLeft_down() {
        return left_down;
    }

}
