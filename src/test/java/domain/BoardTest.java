package domain;

import domain.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
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
}
