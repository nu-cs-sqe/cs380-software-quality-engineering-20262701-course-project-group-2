package domain.piece;

import domain.Location;

import java.util.Set;

public class Rook extends Piece {

    private static final int[][] DIRECTIONS = {
            {0, -1},
            {0, 1},
            {-1, 0},
            {1, 0}
    };

    public Rook(PieceColor color) {
        super(PieceType.ROOK, color);
    }

    @Override
    public Piece makeCopy() {
        return new Rook(getColor());
    }

    public Set<Location> getValidMoves(Location from) {
        return getDirectionalMoves(from, DIRECTIONS);
    }
}
