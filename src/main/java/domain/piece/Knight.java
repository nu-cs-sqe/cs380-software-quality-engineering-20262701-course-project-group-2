package domain.piece;

import domain.Location;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
    private static final int[][] JUMP_OFFSETS = {
            {1, 2},
            {2, 1},
            {2, -1},
            {1, -2},
            {-1, -2},
            {-2, -1},
            {-2, 1},
            {-1, 2}
    };

    public Knight(PieceColor color) {
        super(PieceType.KNIGHT, color);
    }

    @Override
    public Piece makeCopy() {
        return new Knight(getColor());
    }

    public Set<Location> getValidMoves(Location from) {
        Set<Location> moves = new HashSet<>();
        for (int[] offset : JUMP_OFFSETS) {
            int columnDelta = offset[0];
            int rowDelta = offset[1];
            int column = from.getColumn() + columnDelta;
            int row = from.getRow() + rowDelta;
            if (isInsideBoard(column, row)) {
                moves.add(new Location(column, row));
            }
        }
        return moves;
    }
}
