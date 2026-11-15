package domain.piece;

import domain.Location;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class QueenTest {

    // QC1
    @Test
    public void MakeCopy_WhiteQueen_ReturnEquivalentDistinctQueen(){
        Queen queen = new Queen(PieceColor.WHITE);

        Piece copy = queen.makeCopy();

        assertNotSame(queen, copy);
        assertEquals(PieceType.QUEEN, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    // QC2
    @Test
    public void MakeCopy_BlackQueen_ReturnEquivalentDistinctQueen(){
        Queen queen = new Queen(PieceColor.BLACK);

        Piece copy = queen.makeCopy();

        assertNotSame(queen, copy);
        assertEquals(PieceType.QUEEN, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    // M24
    @Test
    public void GetValidMoves_Queen_AllDirectionsFromCentre_returnTwentySeven(){
        Queen queen = new Queen(PieceColor.WHITE);
        Location from = new Location(3, 3);

        Set<Location> expected = Set.of(
                // row 3
                new Location(0, 3),
                new Location(1, 3),
                new Location(2, 3),
                new Location(4, 3),
                new Location(5, 3),
                new Location(6, 3),
                new Location(7, 3),
                // column 3
                new Location(3, 0),
                new Location(3, 1),
                new Location(3, 2),
                new Location(3, 4),
                new Location(3, 5),
                new Location(3, 6),
                new Location(3, 7),
                // diagonal 1
                new Location(0, 0),
                new Location(1, 1),
                new Location(2, 2),
                new Location(4, 4),
                new Location(5, 5),
                new Location(6, 6),
                new Location(7, 7),
                // diagonal 2
                new Location(0, 6),
                new Location(1, 5),
                new Location(2, 4),
                new Location(4, 2),
                new Location(5, 1),
                new Location(6, 0)
        );

        Set<Location> actual = queen.getValidMoves(from);

        assertEquals(27, expected.size());
        assertEquals(expected, actual);
    }

    // M52
    @Test
    public void GetValidMoves_Queen_CornerTopLeft_returnTwentyOne(){
        Queen queen = new Queen(PieceColor.WHITE);
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
                new Location(0, 7),
                // diagonal
                new Location(1, 1),
                new Location(2, 2),
                new Location(3, 3),
                new Location(4, 4),
                new Location(5, 5),
                new Location(6, 6),
                new Location(7, 7)
        );

        Set<Location> actual = queen.getValidMoves(from);

        assertEquals(21, expected.size());
        assertEquals(expected, actual);
    }

    // M53
    @Test
    public void GetValidMoves_Queen_CornerTopRight_returnTwentyOne(){
        Queen queen = new Queen(PieceColor.WHITE);
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
                new Location(7, 7),
                // diagonal
                new Location(6, 1),
                new Location(5, 2),
                new Location(4, 3),
                new Location(3, 4),
                new Location(2, 5),
                new Location(1, 6),
                new Location(0, 7)
        );

        Set<Location> actual = queen.getValidMoves(from);

        assertEquals(21, expected.size());
        assertEquals(expected, actual);
    }

    // M54
    @Test
    public void GetValidMoves_Queen_CornerBottomLeft_returnTwentyOne(){
        Queen queen = new Queen(PieceColor.WHITE);
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
                new Location(0, 6),
                // diagonal
                new Location(1, 6),
                new Location(2, 5),
                new Location(3, 4),
                new Location(4, 3),
                new Location(5, 2),
                new Location(6, 1),
                new Location(7, 0)
        );

        Set<Location> actual = queen.getValidMoves(from);

        assertEquals(21, expected.size());
        assertEquals(expected, actual);
    }

    // M55
    @Test
    public void GetValidMoves_Queen_CornerBottomRight_returnTwentyOne(){
        Queen queen = new Queen(PieceColor.WHITE);
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
                new Location(7, 6),
                // diagonal
                new Location(6, 6),
                new Location(5, 5),
                new Location(4, 4),
                new Location(3, 3),
                new Location(2, 2),
                new Location(1, 1),
                new Location(0, 0)
        );

        Set<Location> actual = queen.getValidMoves(from);

        assertEquals(21, expected.size());
        assertEquals(expected, actual);
    }
}
