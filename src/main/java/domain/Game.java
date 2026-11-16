package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;

import java.util.HashSet;
import java.util.Set;

import static domain.piece.PieceType.PAWN;

public class Game {
    private static final int BOARD_SIZE = 8;

    private final Board board;
    private final PieceColor currentTurn;
    private Piece[][] currentBoardState;
    private Piece currentPiece;

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
        currentBoardState = board.getSnapshot();
        currentPiece = pieceAt(currentBoardState,from);
        Set<Location> returnSet = new HashSet<>();
        for (Location candidate : currentPiece.getValidMoves(from)){
            if (!isBlockedByFriendly(candidate) && isPathClearByType(from, candidate)) {
                returnSet.add(candidate);
            }
        }

        if (currentPiece.getType() == PAWN){
            returnSet.addAll(addPawnCaptures(currentPiece, from));
        }


        return returnSet;
    }

    private static Piece pieceAt(Piece[][] boardState, Location location) {
        return boardState[location.getRow()][location.getColumn()];
    }

    private boolean isPathClearByType(Location from, Location destination){
        switch (currentPiece.getType()) {
            case KNIGHT:
                return true;
            case PAWN:
                return isPathClear(from, destination) && pieceAt(currentBoardState, destination) == null;
            default:
                return isPathClear(from, destination);
        }
    }

    private boolean isBlockedByFriendly(Location destination){
        Piece destinationPiece = pieceAt(currentBoardState,destination);
        return destinationPiece != null && destinationPiece.getColor() == currentPiece.getColor();
    }

    private boolean isPathClear(Location from, Location to) {
        int columnStep = Integer.signum(to.getColumn() - from.getColumn());
        int rowStep = Integer.signum(to.getRow() - from.getRow());

        int column = from.getColumn() + columnStep;
        int row = from.getRow() + rowStep;

        while (column != to.getColumn() || row != to.getRow()) {
            if (pieceAt(currentBoardState, new Location(column, row)) != null) {
                return false;
            }
            column += columnStep;
            row += rowStep;
        }

        return true;
    }

    private Set<Location> addPawnCaptures(Piece pawn, Location from) {
        int direction = pawn.getColor() == PieceColor.WHITE ? -1 : 1;
        int row = from.getRow() + direction;
        Set<Location> captureSet = new HashSet<>();

        for (int columnOffset = -1; columnOffset <= 1; columnOffset += 2) {
            int column = from.getColumn() + columnOffset;

            if (column < 0 || column >= BOARD_SIZE || row < 0 || row >= BOARD_SIZE) {
                continue;
            }

            Location destination = new Location(column, row);

            Piece destinationPiece = pieceAt(currentBoardState, destination);
            if (destinationPiece != null && destinationPiece.getColor() != pawn.getColor()) {
                captureSet.add(destination);
            }
        }

        return captureSet;
    }
}
