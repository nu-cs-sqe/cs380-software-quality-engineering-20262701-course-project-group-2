package view;

import controller.BoardController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final String player1Name;
    private final String player2Name;

    public MainView(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        configureMainView();
        addGameStatsView();
        addBoardView();

        setVisible(true);
        validate();
    }

    private void configureMainView() {
        setTitle("Yiji's Chess Game GUI Example :)!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1200);
        setLocationRelativeTo(null);
    }

    private void addGameStatsView() {
        GameStatsView gameStatsView = new GameStatsView(player1Name, player2Name);
        add(gameStatsView, BorderLayout.PAGE_START);
    }

    private void addBoardView() {
        BoardController boardController = new BoardController();
        BoardView boardView = new BoardView(boardController);
        add(boardView, BorderLayout.CENTER);
    }
}
