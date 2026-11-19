package controller;

import domain.Game;
import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import java.util.Set;

public class BoardController {
    private static final int BOARD_SIZE = 8;

    private final Game game;
    private Location selectedSquare;
    private Set<Location> highlightedSquares = Set.of();

    public BoardController(Game game) {
        this.game = game;
    }

    public Piece[][] getBoardSnapshot() {
        return game.getBoardSnapshot();
    }

    public PieceColor getCurrentTurn() {
        return game.getCurrentTurn();
    }

    public Location getSelectedSquare() {
        return selectedSquare;
    }

    public Set<Location> getHighlightedSquares() {
        return highlightedSquares;
    }

    public void handleSquareClick(Location location) {
        if (!isInsideBoard(location)) {
            return;
        }
        if (highlightedSquares.contains(location)) {
            moveSelectedPieceTo(location);
        } else if (isOwnPiece(location) && !location.equals(selectedSquare)) {
            select(location);
        } else {
            deselect();
        }
    }

    private boolean isInsideBoard(Location location) {
        int column = location.getColumn();
        int row = location.getRow();
        return column >= 0 && column < BOARD_SIZE && row >= 0 && row < BOARD_SIZE;
    }

    private void moveSelectedPieceTo(Location destination) {
        game.movePiece(selectedSquare, destination);
        game.switchTurn();
        deselect();
    }

    private boolean isOwnPiece(Location location) {
        Piece piece = game.getBoardSnapshot()[location.getRow()][location.getColumn()];
        return piece != null && piece.getColor() == game.getCurrentTurn();
    }

    private void select(Location location) {
        selectedSquare = location;
        highlightedSquares = Set.copyOf(game.getLegalMoves(location));
    }

    private void deselect() {
        selectedSquare = null;
        highlightedSquares = Set.of();
    }
}
