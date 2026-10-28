package controller;

import domain.Game;
import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class BoardController {
    private final Game game;

    public BoardController(Game game){
        this.game = game;
    }

    public Piece[][] getBoardSnapshot(){
        return game.getBoardSnapshot();
    }

    public PieceColor getCurrentTurn(){
        return game.getCurrentTurn();
    }

    public void handleSquareClick(Location location){
        // TODO
    }
}