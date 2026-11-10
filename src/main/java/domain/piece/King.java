package domain.piece;

import domain.Location;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
    public King(PieceColor color) {
        super(PieceType.KING, color);
    }

    @Override
    public Piece makeCopy() {
        return new King(getColor());
    }
    public Set<Location> getValidMoves(Location from) {

        return new HashSet<>();
    }

}
