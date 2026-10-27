package domain;

import domain.piece.PieceColor;

public class Game {
    private PieceColor currentTurn;

    public Game() {
        currentTurn = PieceColor.WHITE;
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }
}