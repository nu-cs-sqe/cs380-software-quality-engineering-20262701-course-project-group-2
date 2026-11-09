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

    @Test
    public void MakeCopy_BlackPawn_ReturnEquivalentDistinctPawn(){
        Pawn pawn = new Pawn(PieceColor.BLACK);

        Piece copy = pawn.makeCopy();

        assertNotSame(pawn, copy);
        assertEquals(PieceType.PAWN, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }


    @Test
    public void GetValidMoves_Pawn_OnStartRow_ReturnTwo(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(4, 6);

        Set<Location> expected = Set.of(new Location(4, 5), new Location(4, 4));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

}