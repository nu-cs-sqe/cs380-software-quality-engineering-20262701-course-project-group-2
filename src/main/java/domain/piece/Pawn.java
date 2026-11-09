package domain.piece;
import domain.Location;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(PieceType.PAWN, color);
    }

    @Override
    public Piece makeCopy() {
        return new Pawn(getColor());
    }
    public Set<Location> getValidMoves(Location from) {
        Set<Location> moves = new HashSet<>();
        int column = from.getColumn();
        int row = from.getRow();

        moves.add(new Location(column, row - 1));
        moves.add(new Location(column, row - 2));
        return moves;
    }


}
