package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class WelcomeView extends JFrame {
    private static final int MAX_PLAYER_NAME_LENGTH = 30;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;
    private static final Color BACKGROUND_COLOR = new Color(104, 76, 150);
    private static final String FONT_NAME = "Arial";
    private static final int TITLE_FONT_SIZE = 28;
    private static final int NAME_FONT_SIZE = 16;
    private static final int TEXT_FIELD_COLUMNS = 20;
    private static final int BUTTON_FONT_SIZE = 20;
    private static final int START_BUTTON_ROW = 3;
    private static final int COMPONENT_INSET = 10;

    private JTextField player1NameField;
    private JTextField player2NameField;

    public WelcomeView() {
        configureWelcomeView();
        createWelcomeScreenUI();
    }

    private void configureWelcomeView() {
        setTitle("Welcome Screen!");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createWelcomeScreenUI() {
        JPanel welcomePanel = createWelcomePanel();

        addWelcomeLabel(welcomePanel);
        player1NameField = addPlayerNameInput(
                welcomePanel,
                "Player 1 Name:",
                1
        );
        player2NameField = addPlayerNameInput(
                welcomePanel,
                "Player 2 Name:",
                2
        );
        addStartGameButton(welcomePanel);

        add(welcomePanel);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(BACKGROUND_COLOR);
        return welcomePanel;
    }

    private void addWelcomeLabel(JPanel welcomePanel) {
        JLabel welcomeLabel = new JLabel(
                "Welcome to our Chess Game!",
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        welcomeLabel.setForeground(Color.WHITE);

        welcomePanel.add(
                welcomeLabel,
                createConstraints(0, 0, 2)
        );
    }

    private JTextField addPlayerNameInput(
            JPanel welcomePanel,
            String labelText,
            int row
    ) {
        JLabel nameLabel = new JLabel(labelText);
        nameLabel.setFont(new Font(FONT_NAME, Font.PLAIN, NAME_FONT_SIZE));
        nameLabel.setForeground(Color.WHITE);

        welcomePanel.add(
                nameLabel,
                createConstraints(0, row, 1)
        );

        JTextField nameField = new JTextField(TEXT_FIELD_COLUMNS);
        nameField.setFont(new Font(FONT_NAME, Font.PLAIN, NAME_FONT_SIZE));

        welcomePanel.add(
                nameField,
                createConstraints(1, row, 1)
        );

        return nameField;
    }

    private void addStartGameButton(JPanel welcomePanel) {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font(FONT_NAME, Font.BOLD, BUTTON_FONT_SIZE));
        startGameButton.setOpaque(true);
        startGameButton.setBackground(Color.WHITE);
        startGameButton.setForeground(Color.BLACK);
        startGameButton.setBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true)
        );
        startGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startGameButton.addActionListener(event -> startGame());

        welcomePanel.add(
                startGameButton,
                createConstraints(0, START_BUTTON_ROW, 2)
        );
    }

    private GridBagConstraints createConstraints(
            int column,
            int row,
            int gridWidth
    ) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(
                COMPONENT_INSET,
                COMPONENT_INSET,
                COMPONENT_INSET,
                COMPONENT_INSET
        );
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.gridwidth = gridWidth;
        return constraints;
    }

    private void startGame() {
        String player1Name = player1NameField.getText().strip();
        String player2Name = player2NameField.getText().strip();

        Optional<String> validationError = validatePlayerNames(player1Name, player2Name);

        if (validationError.isPresent()) {
            showValidationError(validationError.get());
            return;
        }

        dispose();
        new MainView(player1Name, player2Name);
    }

    private Optional<String> validatePlayerNames(String player1Name, String player2Name) {
        if (player1Name.isBlank() && player2Name.isBlank()) {
            return Optional.of("Player 1 and Player 2 must each enter a name.");
        }

        if (player1Name.isBlank()) {
            return Optional.of("Player 1 must enter a name.");
        }

        if (player2Name.isBlank()) {
            return Optional.of("Player 2 must enter a name.");
        }

        if (player1Name.length() > MAX_PLAYER_NAME_LENGTH) {
            return Optional.of(String.format(
                    "Player 1's name cannot exceed %d characters.",
                    MAX_PLAYER_NAME_LENGTH
            ));
        }

        if (player2Name.length() > MAX_PLAYER_NAME_LENGTH) {
            return Optional.of(String.format(
                    "Player 2's name cannot exceed %d characters.",
                    MAX_PLAYER_NAME_LENGTH
            ));
        }

        return Optional.empty();
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Player Name",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
