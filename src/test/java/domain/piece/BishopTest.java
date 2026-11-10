package domain.piece;

import domain.Location;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopTest {

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
