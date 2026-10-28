package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    // G1
    @Test
    public void GetCurrentTurn_OnNewGame_ReturnWhite(){
        Game game = new Game();

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = game.getCurrentTurn();

        assertEquals(expected, actual);
    }

    // Integration test for Game & Board
    @Test
    public void NewGame_InitializeBoard(){
        Game game = new Game();

        Piece[][] snapshot = game.getBoardSnapshot();

        int expectedRows = 8;
        int actualRows = snapshot.length;

        assertEquals(expectedRows, actualRows);
    }

    // Integration test verifying everything works properly at setup
    @Test
    public void NewGame_InitializeBoardAndWhiteTurn(){
        Game game = new Game();

        Piece[][] snapshot = game.getBoardSnapshot();
        PieceColor currentTurn = game.getCurrentTurn();

        assertEquals(8, snapshot.length);
        assertEquals(PieceColor.WHITE, currentTurn);
    }
}