package controller;

import domain.Board;
import domain.Location;
import domain.piece.Piece;

public class BoardController {
    private final Board board;

    public BoardController() {
        board = new Board();
    }

    public void handleSquareClick(Location location) {
        // TODO
    }

    public Piece[][] getBoardSnapshot() {
        return board.getSnapshot();
    }
}
