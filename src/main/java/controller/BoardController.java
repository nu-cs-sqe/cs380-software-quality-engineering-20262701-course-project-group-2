package controller;

import domain.Game;
import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class BoardController {
    private final Game game;
    private Location selectedSquare;

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

    public void handleSquareClick(Location location){
        // TODO
    }
}