package domain.piece;

import domain.Location;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class BishopTest {

    // BC1
    @Test
    public void MakeCopy_WhiteBishop_ReturnEquivalentDistinctBishop(){
        Bishop bishop = new Bishop(PieceColor.WHITE);

        Piece copy = bishop.makeCopy();

        assertNotSame(bishop, copy);
        assertEquals(PieceType.BISHOP, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    // BC2
    @Test
    public void MakeCopy_BlackBishop_ReturnEquivalentDistinctBishop(){
        Bishop bishop = new Bishop(PieceColor.BLACK);

        Piece copy = bishop.makeCopy();

        assertNotSame(bishop, copy);
        assertEquals(PieceType.BISHOP, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    // M45
    @Test
    public void GetValidMoves_Bishop_AllDirectionsFromCentre_returnThirteen(){
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Location from = new Location(3, 3);

        Set<Location> expected = Set.of(
                new Location(0, 0),
                new Location(1, 1),
                new Location(2, 2),
                new Location(4, 4),
                new Location(5, 5),
                new Location(6, 6),
                new Location(7, 7),
                new Location(0, 6),
                new Location(1, 5),
                new Location(2, 4),
                new Location(4, 2),
                new Location(5, 1),
                new Location(6, 0)
        );

        Set<Location> actual = bishop.getValidMoves(from);

        assertEquals(13, expected.size());
        assertEquals(expected, actual);
    }

    // M56
    @Test
    public void GetValidMoves_Bishop_CornerTopLeft_returnSeven(){
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Location from = new Location(0, 0);

        Set<Location> expected = Set.of(
                new Location(1, 1),
                new Location(2, 2),
                new Location(3, 3),
                new Location(4, 4),
                new Location(5, 5),
                new Location(6, 6),
                new Location(7, 7)
        );

        Set<Location> actual = bishop.getValidMoves(from);

        assertEquals(7, expected.size());
        assertEquals(expected, actual);
    }

    // M57
    @Test
    public void GetValidMoves_Bishop_CornerTopRight_returnSeven(){
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Location from = new Location(7, 0);

        Set<Location> expected = Set.of(
                new Location(6, 1),
                new Location(5, 2),
                new Location(4, 3),
                new Location(3, 4),
                new Location(2, 5),
                new Location(1, 6),
                new Location(0, 7)
        );

        Set<Location> actual = bishop.getValidMoves(from);

        assertEquals(7, expected.size());
        assertEquals(expected, actual);
    }

    // M58
    @Test
    public void GetValidMoves_Bishop_CornerBottomLeft_returnSeven(){
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Location from = new Location(0, 7);

        Set<Location> expected = Set.of(
                new Location(1, 6),
                new Location(2, 5),
                new Location(3, 4),
                new Location(4, 3),
                new Location(5, 2),
                new Location(6, 1),
                new Location(7, 0)
        );

        Set<Location> actual = bishop.getValidMoves(from);

        assertEquals(7, expected.size());
        assertEquals(expected, actual);
    }

    // M59
    @Test
    public void GetValidMoves_Bishop_CornerBottomRight_returnSeven(){
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Location from = new Location(7, 7);

        Set<Location> expected = Set.of(
                new Location(6, 6),
                new Location(5, 5),
                new Location(4, 4),
                new Location(3, 3),
                new Location(2, 2),
                new Location(1, 1),
                new Location(0, 0)
        );

        Set<Location> actual = bishop.getValidMoves(from);

        assertEquals(7, expected.size());
        assertEquals(expected, actual);
    }
}
