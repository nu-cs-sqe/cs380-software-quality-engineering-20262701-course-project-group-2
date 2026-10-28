package controller;

import domain.Game;
import domain.piece.Piece;
import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardControllerTest {

    @Test
    public void GetBoardSnapshot_FromGame_ReturnInitializedBoard(){
        Game game = new Game();
        BoardController controller = new BoardController(game);

        Piece[][] snapshot = controller.getBoardSnapshot();

        int expectedRows = 8;
        int actualRows = snapshot.length;

        assertEquals(expectedRows, actualRows);
    }

    @Test
    public void GetCurrentTurn_OnNewGame_ReturnWhite(){
        Game game = new Game();
        BoardController controller = new BoardController(game);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = controller.getCurrentTurn();

        assertEquals(expected, actual);
    }
}
