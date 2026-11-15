package domain.piece;

import domain.Location;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
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

    public King(PieceColor color) {
        super(PieceType.KING, color);
    }

    @Override
    public Piece makeCopy() {
        return new King(getColor());
    }

    public Set<Location> getValidMoves(Location from) {
        Set<Location> moves = new HashSet<>();

        for (int[] direction : DIRECTIONS) {
            int column = from.getColumn() + direction[0];
            int row = from.getRow() + direction[1];

            if (isInsideBoard(column, row)) {
                moves.add(new Location(column, row));
            }
        }
        return moves;
    }
}
