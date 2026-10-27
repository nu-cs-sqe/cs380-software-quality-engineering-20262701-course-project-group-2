package controller;

import domain.Game;
import domain.Location;
import domain.piece.Piece;

public class BoardController {
    private final Game game;

    public BoardController(Game game){
        this.game = game;
    }

    public Piece[][] getBoardSnapshot(){
        return game.getBoardSnapshot();
    }

    public void handleSquareClick(Location location){
        // TODO
    }
}