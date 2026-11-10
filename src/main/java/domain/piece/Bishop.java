package domain.piece;

import domain.Location;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

    private static final int[][] DIRECTIONS = {
            {-1, -1},
            {1, 1},
            {-1, 1},
            {1, -1}
    };

    public Bishop(PieceColor color) {
        super(PieceType.BISHOP, color);
    }

    @Override
    public Piece makeCopy() {
        return new Bishop(getColor());
    }


    public Set<Location> getValidMoves(Location from) {
        return getDirectionalMoves(from, DIRECTIONS);
    }
}
