package domain.piece;

import domain.Location;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class KnightTest {

    // NC1
    @Test
    public void MakeCopy_WhiteKnight_ReturnEquivalentDistinctKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        Piece copy = knight.makeCopy();

        assertNotSame(knight, copy);
        assertEquals(PieceType.KNIGHT, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    // NC2
    @Test
    public void MakeCopy_BlackKnight_ReturnEquivalentDistinctKnight() {
        Knight knight = new Knight(PieceColor.BLACK);

        Piece copy = knight.makeCopy();

        assertNotSame(knight, copy);
        assertEquals(PieceType.KNIGHT, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    // M25
    @Test
    public void GetValidMoves_Knight_AllDirectionsFromCentre_ReturnEight() {
        Knight knight = new Knight(PieceColor.WHITE);
        Location from = new Location(3, 3);

        Set<Location> expected = Set.of(
                new Location(1, 2), new Location(1, 4),
                new Location(2, 1), new Location(2, 5),
                new Location(4, 1), new Location(4, 5),
                new Location(5, 2), new Location(5, 4));
        Set<Location> actual = knight.getValidMoves(from);

        assertEquals(expected, actual);
    }

    // M60
    @Test
    public void GetValidMoves_Knight_CornerTopLeft_ReturnTwo() {
        Knight knight = new Knight(PieceColor.WHITE);
        Location from = new Location(0, 0);

        Set<Location> expected = Set.of(new Location(1, 2), new Location(2, 1));
        Set<Location> actual = knight.getValidMoves(from);

        assertEquals(expected, actual);
    }

    // M61
    @Test
    public void GetValidMoves_Knight_CornerTopRight_ReturnTwo() {
        Knight knight = new Knight(PieceColor.WHITE);
        Location from = new Location(7, 0);

        Set<Location> expected = Set.of(new Location(5, 1), new Location(6, 2));
        Set<Location> actual = knight.getValidMoves(from);

        assertEquals(expected, actual);
    }

    // M62
    @Test
    public void GetValidMoves_Knight_CornerBottomLeft_ReturnTwo() {
        Knight knight = new Knight(PieceColor.WHITE);
        Location from = new Location(0, 7);

        Set<Location> expected = Set.of(new Location(1, 5), new Location(2, 6));
        Set<Location> actual = knight.getValidMoves(from);

        assertEquals(expected, actual);
    }

    // M63
    @Test
    public void GetValidMoves_Knight_CornerBottomRight_ReturnTwo() {
        Knight knight = new Knight(PieceColor.WHITE);
        Location from = new Location(7, 7);

        Set<Location> expected = Set.of(new Location(5, 6), new Location(6, 5));
        Set<Location> actual = knight.getValidMoves(from);

        assertEquals(expected, actual);
    }

    // M80
    @Test
    public void GetValidMoves_Knight_OneInFromTopLeftCorner_ReturnFour() {
        Knight knight = new Knight(PieceColor.WHITE);
        Location from = new Location(1, 1);

        Set<Location> expected = Set.of(
                new Location(0, 3), new Location(2, 3),
                new Location(3, 0), new Location(3, 2));
        Set<Location> actual = knight.getValidMoves(from);

        assertEquals(expected, actual);
    }

    // M81
    @Test
    public void GetValidMoves_Knight_OneInFromBottomRightCorner_ReturnFour() {
        Knight knight = new Knight(PieceColor.WHITE);
        Location from = new Location(6, 6);

        Set<Location> expected = Set.of(
                new Location(7, 4), new Location(5, 4),
                new Location(4, 7), new Location(4, 5));
        Set<Location> actual = knight.getValidMoves(from);

        assertEquals(expected, actual);
    }

}
