package domain.piece;

import domain.Location;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class KingTest {

    // KC1
    @Test
    public void MakeCopy_WhiteKing_ReturnEquivalentDistinctKing(){
        King king = new King(PieceColor.WHITE);

        Piece copy = king.makeCopy();

        assertNotSame(king, copy);
        assertEquals(PieceType.KING, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    // KC2
    @Test
    public void MakeCopy_BlackKing_ReturnEquivalentDistinctKing(){
        King king = new King(PieceColor.BLACK);

        Piece copy = king.makeCopy();

        assertNotSame(king, copy);
        assertEquals(PieceType.KING, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    // M22
    @Test
    public void GetValidMoves_King_AllDirectionsFromCentre_returnEight(){
        King king = new King(PieceColor.WHITE);
        Location from = new Location(3, 3);

        Set<Location> expected = Set.of(
                new Location(2, 2),
                new Location(2, 3),
                new Location(2, 4),
                new Location(3, 2),
                new Location(3, 4),
                new Location(4, 2),
                new Location(4, 3),
                new Location(4, 4)
        );

        Set<Location> actual = king.getValidMoves(from);

        assertEquals(8, expected.size());
        assertEquals(expected, actual);
    }

    // M64
    @Test
    public void GetValidMoves_King_CornerTopLeft_returnThree(){
        King king = new King(PieceColor.WHITE);
        Location from = new Location(0, 0);

        Set<Location> expected = Set.of(
                new Location(0, 1),
                new Location(1, 0),
                new Location(1, 1)
        );

        Set<Location> actual = king.getValidMoves(from);

        assertEquals(3, expected.size());
        assertEquals(expected, actual);
    }

    // M65
    @Test
    public void GetValidMoves_King_CornerTopRight_returnThree(){
        King king = new King(PieceColor.WHITE);
        Location from = new Location(7, 0);

        Set<Location> expected = Set.of(
                new Location(6, 0),
                new Location(6, 1),
                new Location(7, 1)
        );

        Set<Location> actual = king.getValidMoves(from);

        assertEquals(3, expected.size());
        assertEquals(expected, actual);
    }

    // M66
    @Test
    public void GetValidMoves_King_CornerBottomLeft_returnThree(){
        King king = new King(PieceColor.WHITE);
        Location from = new Location(0, 7);

        Set<Location> expected = Set.of(
                new Location(0, 6),
                new Location(1, 6),
                new Location(1, 7)
        );

        Set<Location> actual = king.getValidMoves(from);

        assertEquals(3, expected.size());
        assertEquals(expected, actual);
    }

    // M67
    @Test
    public void GetValidMoves_King_CornerBottomRight_returnThree(){
        King king = new King(PieceColor.WHITE);
        Location from = new Location(7, 7);

        Set<Location> expected = Set.of(
                new Location(6, 6),
                new Location(6, 7),
                new Location(7, 6)
        );

        Set<Location> actual = king.getValidMoves(from);

        assertEquals(3, expected.size());
        assertEquals(expected, actual);
    }
}
