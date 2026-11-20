package view;

import controller.BoardController;
import domain.piece.PieceColor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStatsView extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(104, 76, 150);
    private static final String FONT_NAME = "Arial";
    private static final int HEADER_FONT_SIZE = 30;
    private static final int PLAYER_FONT_SIZE = 20;

    private final String player1Name;
    private final String player2Name;
    private final BoardController boardController;
    private final JLabel currentPlayerLabel;

    public GameStatsView(
            String player1Name,
            String player2Name,
            BoardController boardController
    ) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.boardController = boardController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(BACKGROUND_COLOR);

        JLabel playerInfoLabel =
                new JLabel("Player Information");
        playerInfoLabel.setFont(
                new Font(
                        FONT_NAME,
                        Font.BOLD,
                        HEADER_FONT_SIZE
                )
        );
        playerInfoLabel.setForeground(Color.WHITE);

        JLabel player1Label = new JLabel(
                "Player 1: "
                        + player1Name
                        + " (Team: White)"
        );
        player1Label.setFont(
                new Font(
                        FONT_NAME,
                        Font.BOLD,
                        PLAYER_FONT_SIZE
                )
        );
        player1Label.setForeground(Color.WHITE);

        JLabel player2Label = new JLabel(
                "Player 2: "
                        + player2Name
                        + " (Team: Black)"
        );
        player2Label.setFont(
                new Font(
                        FONT_NAME,
                        Font.BOLD,
                        PLAYER_FONT_SIZE
                )
        );
        player2Label.setForeground(Color.WHITE);

        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setFont(
                new Font(
                        FONT_NAME,
                        Font.BOLD,
                        HEADER_FONT_SIZE
                )
        );
        currentPlayerLabel.setForeground(Color.WHITE);

        refreshCurrentPlayer();

        add(playerInfoLabel);
        add(player1Label);
        add(player2Label);
        add(currentPlayerLabel);
    }

    void refreshCurrentPlayer() {
        String currentPlayerName =
                boardController.getCurrentTurn()
                        == PieceColor.WHITE
                        ? player1Name
                        : player2Name;

        currentPlayerLabel.setText(
                "Current Player: " + currentPlayerName
        );
    }

    public String getCurrentPlayerText() {
        return currentPlayerLabel.getText();
    }
}