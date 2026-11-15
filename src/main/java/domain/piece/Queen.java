package domain.piece;

import domain.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {

    private static final int[][] DIRECTIONS = {
            {0, -1},
            {0, 1},
            {-1, 0},
            {1, 0},
            {-1, -1},
            {1, 1},
            {-1, 1},
            {1, -1}
    };

    public Queen(PieceColor color) {
        super(PieceType.QUEEN, color);
    }

    @Override
    public Piece makeCopy() {
        return new Queen(getColor());
    }

    public Set<Location> getValidMoves(Location from) {
        return getDirectionalMoves(from, DIRECTIONS);
    }
}
