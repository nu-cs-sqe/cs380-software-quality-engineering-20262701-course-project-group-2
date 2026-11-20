package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardTest {

    private void assertPiece(Piece piece, PieceType expectedType, PieceColor expectedColor) {
        assertEquals(expectedType, piece.getType());
        assertEquals(expectedColor, piece.getColor());
    }

    // B1
    @Test
    public void GetSnapshot_OnNewBoard_Return8x8Board() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        int expectedRows = 8;
        int actualRows = snapshot.length;
        assertEquals(expectedRows, actualRows);

        for (Piece[] row : snapshot) {
            int expectedColumns = 8;
            int actualColumns = row.length;
            assertEquals(expectedColumns, actualColumns);
        }
    }

    // B2
    @Test
    public void GetSnapshot_OnNewBoard_InitializeBoundaryPositions() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertPiece(snapshot[0][0], PieceType.ROOK, PieceColor.BLACK);
        assertPiece(snapshot[7][7], PieceType.ROOK, PieceColor.WHITE);
    }

    // B3
    @Test
    public void GetSnapshot_OnNewBoard_ReturnCorrectPieceCounts() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        int occupiedSquares = 0;
        int blackPieces = 0;
        int whitePieces = 0;
        int emptySquares = 0;

        for (Piece[] row : snapshot) {
            for (Piece piece : row) {
                if (piece == null) {
                    emptySquares++;
                } else {
                    occupiedSquares++;

                    if (piece.getColor() == PieceColor.BLACK) {
                        blackPieces++;
                    } else if (piece.getColor() == PieceColor.WHITE) {
                        whitePieces++;
                    }
                }
            }
        }

        assertEquals(32, occupiedSquares);
        assertEquals(16, blackPieces);
        assertEquals(16, whitePieces);
        assertEquals(32, emptySquares);
    }

    // B4
    @Test
    public void GetSnapshot_OnNewBoard_PlaceAllPiecesInStartingPositions() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        PieceType[] majorPieces = {
                PieceType.ROOK,
                PieceType.KNIGHT,
                PieceType.BISHOP,
                PieceType.QUEEN,
                PieceType.KING,
                PieceType.BISHOP,
                PieceType.KNIGHT,
                PieceType.ROOK
        };

        for (int column = 0; column < 8; column++) {
            assertPiece(snapshot[0][column], majorPieces[column], PieceColor.BLACK);
            assertPiece(snapshot[1][column], PieceType.PAWN, PieceColor.BLACK);

            assertNull(snapshot[2][column]);
            assertNull(snapshot[3][column]);
            assertNull(snapshot[4][column]);
            assertNull(snapshot[5][column]);

            assertPiece(snapshot[6][column], PieceType.PAWN, PieceColor.WHITE);
            assertPiece(snapshot[7][column], majorPieces[column], PieceColor.WHITE);
        }
    }

    // M37
    @Test
    public void MovePiece_ToEmptySquare_RelocatePiece() {
        Board board = new Board();
        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        board.movePiece(from, to);

        Piece[][] snapshot = board.getSnapshot();

        assertNull(snapshot[6][4]);
        assertPiece(
                snapshot[5][4],
                PieceType.PAWN,
                PieceColor.WHITE
        );
    }

    // M38
    @Test
    public void MovePiece_ToEmptySquareTwoRows_RelocatePiece() {
        Board board = new Board();
        Location from = new Location(4, 6);
        Location to = new Location(4, 4);

        board.movePiece(from, to);

        Piece[][] snapshot = board.getSnapshot();

        assertNull(snapshot[6][4]);
        assertPiece(
                snapshot[4][4],
                PieceType.PAWN,
                PieceColor.WHITE
        );
    }

    // M39
    @Test
    public void MovePiece_ToOccupiedSquare_CaptureReplacesDestination() {
        Board board = new Board();

        board.movePiece(
                new Location(4, 6),
                new Location(4, 4)
        );
        board.movePiece(
                new Location(5, 1),
                new Location(5, 3)
        );

        board.movePiece(
                new Location(4, 4),
                new Location(5, 3)
        );

        Piece[][] snapshot = board.getSnapshot();

        assertNull(snapshot[4][4]);
        assertPiece(
                snapshot[3][5],
                PieceType.PAWN,
                PieceColor.WHITE
        );

        int occupiedSquares = 0;

        for (Piece[] row : snapshot) {
            for (Piece piece : row) {
                if (piece != null) {
                    occupiedSquares++;
                }
            }
        }

        int expectedOccupiedSquares = 31;
        assertEquals(expectedOccupiedSquares, occupiedSquares);
    }
}
