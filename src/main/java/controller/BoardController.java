package controller;

import domain.Game;
import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;

import java.util.Set;

public class BoardController {
    private final Game game;
    private Location selectedSquare;
    private Set<Location> highlightedSquares = Set.of();

    public BoardController(Game game){
        this.game = game;
    }

    public Piece[][] getBoardSnapshot(){
        return game.getBoardSnapshot();
    }

    public PieceColor getCurrentTurn(){
        return game.getCurrentTurn();
    }

    public Location getSelectedSquare(){
        return selectedSquare;
    }

    public Set<Location> getHighlightedSquares(){
        return highlightedSquares;
    }

    public void handleSquareClick(Location location){
        // TODO
    }
}