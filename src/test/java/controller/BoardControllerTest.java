package controller;

import domain.Game;
import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardControllerTest {

    private Game mockGame;
    private BoardController controller;

    @BeforeEach
    public void setUp(){
        mockGame = createMock(Game.class);
        controller = new BoardController(mockGame);
    }

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

    @Test
    public void GetSelectedSquare_NoClick_ReturnNull(){
        replay(mockGame);

        Location actual = controller.getSelectedSquare();

        assertNull(actual);
        verify(mockGame);
    }
}
