package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private static final int BOARD_SIZE = 8;

    private final Board board;
    private final PieceColor currentTurn;

    public Game() {
        this(new Board());
    }

    Game(Board board) {
        this.board = board;
        currentTurn = PieceColor.WHITE;
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public Piece[][] getBoardSnapshot() {
        return board.getSnapshot();
    }

    public Set<Location> getLegalMoves(Location from) {
        Piece[][] boardState = board.getSnapshot();
        Piece piece = boardState[from.getRow()][from.getColumn()];
        Set<Location> returnSet = new HashSet<>();
        return new HashSet<>();
    }
}
