package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private static final int BOARD_SIZE = 8;

    private final Board board;
    private final PieceColor currentTurn;

    //current board state/piece in play
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
            if (!isBlockedByFriendly(candidate)) {
                returnSet.add(candidate);
            }
        }

        return returnSet;
    }

    private boolean isBlockedByFriendly(Location destination){
        Piece destinationPiece = pieceAt(currentBoardState,destination);
        return destinationPiece == null || destinationPiece.getColor() != currentPiece.getColor();
    }


    private static Piece pieceAt(Piece[][] boardState, Location location) {
        return boardState[location.getRow()][location.getColumn()];
    }
}
