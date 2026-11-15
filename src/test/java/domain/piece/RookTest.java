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

    // M48
    @Test
    public void GetValidMoves_Rook_CornerTopLeft_returnFourteen(){
        Rook rook = new Rook(PieceColor.WHITE);
        Location from = new Location(0, 0);

        Set<Location> expected = Set.of(
                // row 0
                new Location(1, 0),
                new Location(2, 0),
                new Location(3, 0),
                new Location(4, 0),
                new Location(5, 0),
                new Location(6, 0),
                new Location(7, 0),
                // column 0
                new Location(0, 1),
                new Location(0, 2),
                new Location(0, 3),
                new Location(0, 4),
                new Location(0, 5),
                new Location(0, 6),
                new Location(0, 7)
        );

        Set<Location> actual = rook.getValidMoves(from);

        assertEquals(14, expected.size());
        assertEquals(expected, actual);
    }

    // M49
    @Test
    public void GetValidMoves_Rook_CornerTopRight_returnFourteen(){
        Rook rook = new Rook(PieceColor.WHITE);
        Location from = new Location(7, 0);

        Set<Location> expected = Set.of(
                // row 0
                new Location(0, 0),
                new Location(1, 0),
                new Location(2, 0),
                new Location(3, 0),
                new Location(4, 0),
                new Location(5, 0),
                new Location(6, 0),
                // column 7
                new Location(7, 1),
                new Location(7, 2),
                new Location(7, 3),
                new Location(7, 4),
                new Location(7, 5),
                new Location(7, 6),
                new Location(7, 7)
        );

        Set<Location> actual = rook.getValidMoves(from);

        assertEquals(14, expected.size());
        assertEquals(expected, actual);
    }

    // M50
    @Test
    public void GetValidMoves_Rook_CornerBottomLeft_returnFourteen(){
        Rook rook = new Rook(PieceColor.WHITE);
        Location from = new Location(0, 7);

        Set<Location> expected = Set.of(
                // row 7
                new Location(1, 7),
                new Location(2, 7),
                new Location(3, 7),
                new Location(4, 7),
                new Location(5, 7),
                new Location(6, 7),
                new Location(7, 7),
                // column 0
                new Location(0, 0),
                new Location(0, 1),
                new Location(0, 2),
                new Location(0, 3),
                new Location(0, 4),
                new Location(0, 5),
                new Location(0, 6)
        );

        Set<Location> actual = rook.getValidMoves(from);

        assertEquals(14, expected.size());
        assertEquals(expected, actual);
    }

    // M51
    @Test
    public void GetValidMoves_Rook_CornerBottomRight_returnFourteen(){
        Rook rook = new Rook(PieceColor.WHITE);
        Location from = new Location(7, 7);

        Set<Location> expected = Set.of(
                // row 7
                new Location(0, 7),
                new Location(1, 7),
                new Location(2, 7),
                new Location(3, 7),
                new Location(4, 7),
                new Location(5, 7),
                new Location(6, 7),
                // column 7
                new Location(7, 0),
                new Location(7, 1),
                new Location(7, 2),
                new Location(7, 3),
                new Location(7, 4),
                new Location(7, 5),
                new Location(7, 6)
        );

        Set<Location> actual = rook.getValidMoves(from);

        assertEquals(14, expected.size());
        assertEquals(expected, actual);
    }
}
