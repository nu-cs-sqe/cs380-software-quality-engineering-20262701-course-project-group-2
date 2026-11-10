package domain.piece;

import domain.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {


    public Queen(PieceColor color) {
        super(PieceType.QUEEN, color);
    }

    @Override
    public Piece makeCopy() {
        return new Queen(getColor());
    }

    public Set<Location> getValidMoves(Location from) {
        return new HashSet<>();
    }
}
