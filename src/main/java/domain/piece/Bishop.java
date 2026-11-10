package domain.piece;

import domain.Location;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(PieceType.BISHOP, color);
    }

    @Override
    public Piece makeCopy() {
        return new Bishop(getColor());
    }

    public Set<Location> getValidMoves() {
        return new HashSet<>();
    }
}
