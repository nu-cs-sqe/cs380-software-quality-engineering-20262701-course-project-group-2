package domain.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class KnightTest {

    @Test
    public void MakeCopy_WhiteKnight_ReturnEquivalentDistinctKnight(){
        Knight knight = new Knight(PieceColor.WHITE);

        Piece copy = knight.makeCopy();

        assertNotSame(knight, copy);
        assertEquals(PieceType.KNIGHT, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }
}