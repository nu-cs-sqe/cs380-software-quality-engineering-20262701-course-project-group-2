package domain;

import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Queen;
import domain.piece.Rook;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;

public class Board {
    private static final int BOARD_SIZE = 8;
    private static final int BLACK_PIECE_ROW = 0;
    private static final int BLACK_PAWN_ROW = 1;
    private static final int WHITE_PAWN_ROW = 6;
    private static final int WHITE_PIECE_ROW = 7;
    private static final int LEFT_ROOK_COLUMN = 0;
    private static final int LEFT_KNIGHT_COLUMN = 1;
    private static final int LEFT_BISHOP_COLUMN = 2;
    private static final int QUEEN_COLUMN = 3;
    private static final int KING_COLUMN = 4;
    private static final int RIGHT_BISHOP_COLUMN = 5;
    private static final int RIGHT_KNIGHT_COLUMN = 6;
    private static final int RIGHT_ROOK_COLUMN = 7;

    private final Piece[][] pieces;

    public Board() {
        pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        initializePieces(BLACK, BLACK_PIECE_ROW, BLACK_PAWN_ROW);
        initializePieces(WHITE, WHITE_PIECE_ROW, WHITE_PAWN_ROW);
    }

    private void initializePieces(PieceColor color, int pieceRow, int pawnRow) {
        for (int column = 0; column < BOARD_SIZE; column++) {
            pieces[pawnRow][column] = new Pawn(color);
        }

        pieces[pieceRow][LEFT_ROOK_COLUMN] = new Rook(color);
        pieces[pieceRow][LEFT_KNIGHT_COLUMN] = new Knight(color);
        pieces[pieceRow][LEFT_BISHOP_COLUMN] = new Bishop(color);
        pieces[pieceRow][QUEEN_COLUMN] = new Queen(color);
        pieces[pieceRow][KING_COLUMN] = new King(color);
        pieces[pieceRow][RIGHT_BISHOP_COLUMN] = new Bishop(color);
        pieces[pieceRow][RIGHT_KNIGHT_COLUMN] = new Knight(color);
        pieces[pieceRow][RIGHT_ROOK_COLUMN] = new Rook(color);
    }

    public Piece[][] getSnapshot() {
        Piece[][] snapshot = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (pieces[row][column] != null) {
                    snapshot[row][column] = pieces[row][column].makeCopy();
                }
            }
        }
        return snapshot;
    }

    public void movePiece(Location from, Location to) {
        Piece movingPiece = pieces[from.getRow()][from.getColumn()];

        pieces[from.getRow()][from.getColumn()] = null;
        pieces[to.getRow()][to.getColumn()] = movingPiece;
    }
}
