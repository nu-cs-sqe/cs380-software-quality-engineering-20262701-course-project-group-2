package domain.piece;

import domain.Location;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class RookTest {

    // RC1
    @Test
    public void MakeCopy_WhiteRook_ReturnEquivalentDistinctRook(){
        Rook rook = new Rook(PieceColor.WHITE);

        Piece copy = rook.makeCopy();

        assertNotSame(rook, copy);
        assertEquals(PieceType.ROOK, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    // RC2
    @Test
    public void MakeCopy_BlackRook_ReturnEquivalentDistinctRook(){
        Rook rook = new Rook(PieceColor.BLACK);

        Piece copy = rook.makeCopy();

        assertNotSame(rook, copy);
        assertEquals(PieceType.ROOK, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    // M8
    @Test
    public void GetValidMoves_Rook_AllTilesUnblocked_ReturnAllRowAndColumn(){
        Rook rook = new Rook(PieceColor.WHITE);
        Location from = new Location(7, 7);

        Set<Location> expected = Set.of(
                new Location(0, 7),
                new Location(1, 7),
                new Location(2, 7),
                new Location(3, 7),
                new Location(4, 7),
                new Location(5, 7),
                new Location(6, 7),
                new Location(7, 0),
                new Location(7, 1),
                new Location(7, 2),
                new Location(7, 3),
                new Location(7, 4),
                new Location(7, 5),
                new Location(7, 6)
        );

        Set<Location> actual = rook.getValidMoves(from);

        assertEquals(expected, actual);
    }
}
