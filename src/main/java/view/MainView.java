package view;

import controller.BoardController;
import domain.Game;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainView extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 1200;

    private final String player1Name;
    private final String player2Name;
    private final BoardController boardController;

    public MainView(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        Game game = new Game();
        boardController = new BoardController(game);

        configureMainView();
        addGameStatsView();
        addBoardView();

        setVisible(true);
        validate();
    }

    private void configureMainView() {
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    private void addGameStatsView() {
        GameStatsView gameStatsView = new GameStatsView(
                player1Name,
                player2Name,
                boardController
        );
        add(gameStatsView, BorderLayout.PAGE_START);
    }

    private void addBoardView() {
        BoardView boardView = new BoardView(boardController);
        add(boardView, BorderLayout.CENTER);
    }
}
