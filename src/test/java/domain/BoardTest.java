package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    private void assertPiece(Piece piece, PieceType expectedType, PieceColor expectedColor){
        assertEquals(expectedType, piece.getType());
        assertEquals(expectedColor, piece.getColor());
    }

    // B1
    @Test
    public void GetSnapshot_OnNewBoard_Return8x8Board(){
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        int expectedRows = 8;
        int actualRows = snapshot.length;
        assertEquals(expectedRows, actualRows);

        for(Piece[] row : snapshot){
            int expectedColumns = 8;
            int actualColumns = row.length;
            assertEquals(expectedColumns, actualColumns);
        }
    }

    // B2
    @Test
    public void GetSnapshot_OnNewBoard_InitializeBoundaryPositions(){
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertPiece(snapshot[0][0], PieceType.ROOK, PieceColor.BLACK);
        assertPiece(snapshot[7][7], PieceType.ROOK, PieceColor.WHITE);
    }

    // B3
    @Test
    public void GetSnapshot_OnNewBoard_ReturnCorrectPieceCounts(){
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        int occupiedSquares = 0;
        int blackPieces = 0;
        int whitePieces = 0;
        int emptySquares = 0;

        for(Piece[] row : snapshot){
            for(Piece piece : row){
                if(piece == null){
                    emptySquares++;
                } else {
                    occupiedSquares++;

                    if(piece.getColor() == PieceColor.BLACK){
                        blackPieces++;
                    } else if(piece.getColor() == PieceColor.WHITE){
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
}
