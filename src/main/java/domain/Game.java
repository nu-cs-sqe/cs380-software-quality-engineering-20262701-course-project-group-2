package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;

import java.util.Set;

public class Game {
    private final Board board;
    private PieceColor currentTurn;

    public Game(){
        board = new Board();
        currentTurn = PieceColor.WHITE;
    }

    public PieceColor getCurrentTurn(){
        return currentTurn;
    }

    public Piece[][] getBoardSnapshot(){
        return board.getSnapshot();
    }

    public Set<Location> getLegalMoves(Location location){
        throw new UnsupportedOperationException("getLegalMoves not implemented yet (It is on annie's branch currently)");
    }


}