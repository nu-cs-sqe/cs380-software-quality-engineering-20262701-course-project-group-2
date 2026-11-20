package view;

import controller.BoardController;
import domain.piece.PieceColor;
import java.awt.BorderLayout;
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
    private static final int TURN_FONT_SIZE = 34;

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

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(BACKGROUND_COLOR);

        final JPanel playerInfoPanel = createPlayerInfoPanel();

        JPanel currentTurnPanel = new JPanel();
        currentTurnPanel.setLayout(new BoxLayout(currentTurnPanel, BoxLayout.Y_AXIS));
        currentTurnPanel.setOpaque(false);

        JLabel currentTurnLabel = new JLabel("CURRENT TURN");
        currentTurnLabel.setFont(new Font(FONT_NAME, Font.BOLD, PLAYER_FONT_SIZE));
        currentTurnLabel.setForeground(Color.WHITE);

        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setFont(new Font(FONT_NAME, Font.BOLD, TURN_FONT_SIZE));
        currentPlayerLabel.setForeground(Color.WHITE);

        refreshCurrentPlayer();

        currentTurnPanel.add(currentTurnLabel);
        currentTurnPanel.add(currentPlayerLabel);

        add(playerInfoPanel, BorderLayout.WEST);
        add(currentTurnPanel, BorderLayout.EAST);
    }

    private JPanel createPlayerInfoPanel() {
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        playerInfoPanel.setOpaque(false);

        JLabel playerInfoLabel = new JLabel("Player Information");
        playerInfoLabel.setFont(new Font(FONT_NAME, Font.BOLD, HEADER_FONT_SIZE));
        playerInfoLabel.setForeground(Color.WHITE);

        JLabel player1Label = new JLabel(
                "\t\t\tPlayer 1: " + player1Name + " (Team: White)"
        );
        player1Label.setFont(new Font(FONT_NAME, Font.BOLD, PLAYER_FONT_SIZE));
        player1Label.setForeground(Color.WHITE);

        JLabel player2Label = new JLabel(
                "\t\t\tPlayer 2: " + player2Name + " (Team: Black)"
        );
        player2Label.setFont(new Font(FONT_NAME, Font.BOLD, PLAYER_FONT_SIZE));
        player2Label.setForeground(Color.WHITE);

        playerInfoPanel.add(playerInfoLabel);
        playerInfoPanel.add(player1Label);
        playerInfoPanel.add(player2Label);

        return playerInfoPanel;
    }

    void refreshCurrentPlayer() {
        boolean isWhiteTurn = boardController.getCurrentTurn() == PieceColor.WHITE;

        String currentPlayerName = isWhiteTurn
                ? player1Name
                : player2Name;

        String currentPlayerColor = isWhiteTurn
                ? "White"
                : "Black";

        currentPlayerLabel.setText(
                currentPlayerName + " (" + currentPlayerColor + ")"
        );
    }

    public String getCurrentPlayerText() {
        return currentPlayerLabel.getText();
    }
}
