package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static domain.piece.PieceType.PAWN;

public class Game {
    private static final int BOARD_SIZE = 8;

    private final Board board;
    private PieceColor currentTurn;

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

    public void movePiece(Location from, Location to) {
        board.movePiece(from, to);
    }

    public void switchTurn() {
        currentTurn = currentTurn == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }

    public Set<Location> getLegalMoves(Location from) {
        Piece[][] boardSnapshot = board.getSnapshot();
        Piece currentPiece = pieceAt(boardSnapshot, from);
        Set<Location> legalMoves = new HashSet<>();

        for (Location candidate : currentPiece.getValidMoves(from)) {
            boolean pathAvailable = isPathAvailableForPiece(boardSnapshot, currentPiece, from, candidate);
            if (!isBlockedByFriendly(boardSnapshot, currentPiece, candidate) && pathAvailable) {
                legalMoves.add(candidate);
            }
        }

        if (currentPiece.getType() == PAWN) {
            legalMoves.addAll(getPawnCaptures(boardSnapshot, currentPiece, from));
        }

        return Collections.unmodifiableSet(legalMoves);
    }

    private static Piece pieceAt(Piece[][] boardState, Location location) {
        return boardState[location.getRow()][location.getColumn()];
    }

    private static boolean isPathAvailableForPiece(
            Piece[][] boardState, Piece piece, Location from, Location destination) {
        switch (piece.getType()) {
            case KNIGHT:
                return true;
            case PAWN:
                return isPathClear(boardState, from, destination)
                        && pieceAt(boardState, destination) == null;
            default:
                return isPathClear(boardState, from, destination);
        }
    }

    private static boolean isBlockedByFriendly(
            Piece[][] boardState, Piece piece, Location destination) {
        Piece destinationPiece = pieceAt(boardState, destination);
        return destinationPiece != null && destinationPiece.getColor() == piece.getColor();
    }

    private static boolean isPathClear(Piece[][] boardState, Location from, Location to) {
        int columnStep = Integer.signum(to.getColumn() - from.getColumn());
        int rowStep = Integer.signum(to.getRow() - from.getRow());

        int column = from.getColumn() + columnStep;
        int row = from.getRow() + rowStep;

        while (column != to.getColumn() || row != to.getRow()) {
            if (pieceAt(boardState, new Location(column, row)) != null) {
                return false;
            }
            column += columnStep;
            row += rowStep;
        }

        return true;
    }

    private static Set<Location> getPawnCaptures(Piece[][] boardState, Piece pawn, Location from) {
        int direction = pawn.getColor() == PieceColor.WHITE ? -1 : 1;
        int row = from.getRow() + direction;
        Set<Location> captureSet = new HashSet<>();

        for (int columnOffset = -1; columnOffset <= 1; columnOffset += 2) {
            int column = from.getColumn() + columnOffset;

            if (column < 0 || column >= BOARD_SIZE || row < 0 || row >= BOARD_SIZE) {
                continue;
            }

            Location destination = new Location(column, row);

            Piece destinationPiece = pieceAt(boardState, destination);

            if (destinationPiece != null && destinationPiece.getColor() != pawn.getColor()) {
                captureSet.add(destination);
            }
        }

        return captureSet;
    }
}
