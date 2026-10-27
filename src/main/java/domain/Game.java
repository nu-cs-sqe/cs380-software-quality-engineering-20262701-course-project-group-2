package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;

public class Game {
    private final Board board;
    private PieceColor currentTurn;

    public Game(){
        board = new Board();
        currentTurn = PieceColor.WHITE;
    }

    public PieceColor getCurrentTurn(){
        return currentTurn;
    }

    public Piece[][] getBoardSnapshot(){
        return board.getSnapshot();
    }
}