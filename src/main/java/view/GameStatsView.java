package view;

import controller.BoardController;
import domain.piece.PieceColor;

import javax.swing.*;
import java.awt.*;

public class GameStatsView extends JPanel {
    private final JLabel currentPlayerLabel;

    public GameStatsView(String player1Name, String player2Name, BoardController boardController) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(new Color(104, 76, 150));

        JLabel playerInfoLabel = new JLabel("Player Information");
        playerInfoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        playerInfoLabel.setForeground(Color.WHITE);

        JLabel player1Label = new JLabel("\t\t\tPlayer 1: " + player1Name + " (Team: White)");
        player1Label.setFont(new Font("Arial", Font.BOLD, 20));
        player1Label.setForeground(Color.WHITE);

        JLabel player2Label = new JLabel("\t\t\tPlayer 2: " + player2Name + " (Team: Black)");
        player2Label.setFont(new Font("Arial", Font.BOLD, 20));
        player2Label.setForeground(Color.WHITE);

        String currentPlayerName = boardController.getCurrentTurn() == PieceColor.WHITE
                ? player1Name
                : player2Name;

        currentPlayerLabel = new JLabel("Current Player: " + currentPlayerName);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        currentPlayerLabel.setForeground(Color.WHITE);

        add(playerInfoLabel);
        add(player1Label);
        add(player2Label);
        add(currentPlayerLabel);
    }

    public String getCurrentPlayerText() {
        return currentPlayerLabel.getText();
    }
}