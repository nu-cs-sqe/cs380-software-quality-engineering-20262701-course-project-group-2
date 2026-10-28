package view;

import controller.BoardController;
import domain.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStatsViewTest {

    @Test
    public void GameStatsView_OnNewGame_DisplayWhitePlayer(){
        Game game = new Game();
        BoardController controller = new BoardController(game);

        GameStatsView view = new GameStatsView(
                "Alice",
                "Bob",
                controller
        );

        String expected = "Current Player: Alice";
        String actual = view.getCurrentPlayerText();

        assertEquals(expected, actual);
    }
}