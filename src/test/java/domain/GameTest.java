package domain;

import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void GetCurrentTurn_OnNewGame_ReturnWhite(){
        Game game = new Game();

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = game.getCurrentTurn();

        assertEquals(expected, actual);
    }
}