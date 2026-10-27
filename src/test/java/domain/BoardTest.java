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

    @Test
    public void GetSnapshot_OnNewBoard_InitializeBoundaryPositions(){
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertPiece(snapshot[0][0], PieceType.ROOK, PieceColor.BLACK);
        assertPiece(snapshot[7][7], PieceType.ROOK, PieceColor.WHITE);
    }
}
