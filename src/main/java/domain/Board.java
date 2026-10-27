package domain;

import domain.piece.*;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;

public class Board {
    private static final int BOARD_SIZE = 8;

    private final Piece[][] pieces;

    public Board() {
        pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        initializePieces(BLACK, 0, 1);
        initializePieces(WHITE, 7, 6);
    }

    private void initializePieces(PieceColor color, int pieceRow, int pawnRow) {
        for (int column = 0; column < BOARD_SIZE; column++) {
            pieces[pawnRow][column] = new Pawn(color);
        }

        pieces[pieceRow][0] = new Rook(color);
        pieces[pieceRow][1] = new Knight(color);
        pieces[pieceRow][2] = new Bishop(color);
        pieces[pieceRow][3] = new Queen(color);
        pieces[pieceRow][4] = new King(color);
        pieces[pieceRow][5] = new Bishop(color);
        pieces[pieceRow][6] = new Knight(color);
        pieces[pieceRow][7] = new Rook(color);
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
}
