package domain.piece;

import domain.Location;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieceTest {

    @Test
    public void GetDirectionalMoves_NullDirections_ReturnEmptySet() {
        Piece piece = new TestPiece();
        Location from = new Location(3, 3);

        Set<Location> actual = piece.getDirectionalMoves(from, null);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void GetDirectionalMoves_ZeroDirection_ReturnEmptySet() {
        Piece piece = new TestPiece();
        Location from = new Location(3, 3);
        int[][] directions = {
                {0, 0}
        };

        Set<Location> actual = piece.getDirectionalMoves(
                from,
                directions
        );

        assertTrue(actual.isEmpty());
    }

    @Test
    public void ToString_WhiteRook_ReturnColorAndType() {
        Piece piece = new TestPiece();

        String expected = "WHITE ROOK";
        String actual = piece.toString();

        assertEquals(expected, actual);
    }

    private static final class TestPiece extends Piece {

        private TestPiece() {
            super(PieceType.ROOK, PieceColor.WHITE);
        }

        @Override
        public Piece makeCopy() {
            return new TestPiece();
        }
    }
}