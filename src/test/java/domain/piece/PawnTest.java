package domain.piece;

import domain.Location;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class PawnTest {

    @Test
    public void MakeCopy_WhitePawn_ReturnEquivalentDistinctPawn(){
        Pawn pawn = new Pawn(PieceColor.WHITE);

        Piece copy = pawn.makeCopy();

        assertNotSame(pawn, copy);
        assertEquals(PieceType.PAWN, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }
}