package view;

import controller.BoardController;
import domain.Game;
import java.awt.BorderLayout;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;

public class MainView extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 1200;

    private final String player1Name;
    private final String player2Name;
    private final BoardController boardController;
    private final Locale locale;
    private GameStatsView gameStatsView;

    public MainView(
            String player1Name,
            String player2Name,
            Locale locale
    ) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.locale = locale;

        Game game = new Game();
        boardController = new BoardController(game);

        configureMainView();
        addGameStatsView();
        addBoardView();

        setVisible(true);
        validate();
    }

    private void configureMainView() {
        ResourceBundle labels = ResourceBundle.getBundle("labels", locale);

        setTitle(labels.getString("gameWindowTitle"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    private void addGameStatsView() {
        gameStatsView = new GameStatsView(
                player1Name,
                player2Name,
                boardController,
                locale
        );
        add(gameStatsView, BorderLayout.PAGE_START);
    }

    private void addBoardView() {
        BoardView boardView = new BoardView(
                boardController,
                gameStatsView::refreshCurrentPlayer
        );
        add(boardView, BorderLayout.CENTER);
    }
}
