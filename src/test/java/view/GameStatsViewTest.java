package view;

import controller.BoardController;
import domain.Game;
import java.util.Locale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStatsViewTest {

    @Test
    public void GameStatsView_OnNewGame_DisplayWhitePlayer() {
        Game game = new Game();
        BoardController controller = new BoardController(game);

        GameStatsView view = new GameStatsView(
                "Alice",
                "Bob",
                controller,
                Locale.ENGLISH
        );

        String expected = "Alice (White)";
        String actual = view.getCurrentPlayerText();

        assertEquals(expected, actual);
    }

    // GS2
    @Test
    public void RefreshCurrentPlayer_AfterTurnChanges_DisplayBlackPlayer() {
        Game game = new Game();
        BoardController controller = new BoardController(game);

        GameStatsView view = new GameStatsView(
                "Alice",
                "Bob",
                controller,
                Locale.ENGLISH
        );

        game.switchTurn();

        view.refreshCurrentPlayer();

        String expected = "Bob (Black)";
        String actual = view.getCurrentPlayerText();

        assertEquals(expected, actual);
    }

    @Test
    public void GameStatsView_SpanishLocale_DisplaySpanishCurrentPlayer() {
        Game game = new Game();
        BoardController controller = new BoardController(game);

        GameStatsView view = new GameStatsView(
                "Alice",
                "Bob",
                controller,
                Locale.forLanguageTag("es")
        );

        assertEquals(
                "Alice (Blanco)",
                view.getCurrentPlayerText()
        );
    }

    @Test
    public void GameStatsView_FrenchLocale_DisplayFrenchCurrentPlayer() {
        Game game = new Game();
        BoardController controller = new BoardController(game);

        GameStatsView view = new GameStatsView(
                "Alice",
                "Bob",
                controller,
                Locale.FRENCH
        );

        assertEquals(
                "Alice (Blanc)",
                view.getCurrentPlayerText()
        );
    }
}
