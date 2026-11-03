package domain.piece;

import domain.Location;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece {
    private static final int BOARD_SIZE = 8;

    private final PieceType type;
    private final PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract Piece makeCopy();

    private Set<Location> getDirectionalMoves(Location from, int[][] directions) {
        Set<Location> moves = new HashSet<>();

        if(directions == null){
            return moves;
        }

        for(int[] direction : directions){
            int columnDelta = direction[0];
            int rowDelta = direction[1];

            if(columnDelta == 0 && rowDelta == 0){
                continue;
            }

            int column = from.getColumn() + columnDelta;
            int row = from.getRow() + rowDelta;

            while(isInsideBoard(column, row)){
                moves.add(new Location(column, row));
                column += columnDelta;
                row += rowDelta;
            }
        }

        return moves;
    }

    private boolean isInsideBoard(int column, int row) {
        return column >= 0 && column < BOARD_SIZE && row >= 0 && row < BOARD_SIZE;
    }

    @Override
    public String toString() {
        return String.format("%s %s", color.name(), type.name());
    }
}
