package view;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JFrame {
    private JTextField player1NameField;
    private JTextField player2NameField;

    public WelcomeView() {
        configureWelcomeView();
        createWelcomeScreenUI();
    }

    private void configureWelcomeView() {
        setTitle("Welcome Screen!");
        setSize(600, 600);
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
        welcomePanel.setBackground(new Color(104, 76, 150));
        return welcomePanel;
    }

    private void addWelcomeLabel(JPanel welcomePanel) {
        JLabel welcomeLabel = new JLabel(
                "Welcome to our Chess Game!",
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
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
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(Color.WHITE);

        welcomePanel.add(
                nameLabel,
                createConstraints(0, row, 1)
        );

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));

        welcomePanel.add(
                nameField,
                createConstraints(1, row, 1)
        );

        return nameField;
    }

    private void addStartGameButton(JPanel welcomePanel) {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        startGameButton.setOpaque(true);
        startGameButton.setBackground(Color.WHITE);
        startGameButton.setForeground(Color.BLACK);
        startGameButton.setBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true)
        );
        startGameButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        startGameButton.addActionListener(event -> startGame());

        welcomePanel.add(
                startGameButton,
                createConstraints(0, 3, 2)
        );
    }

    private GridBagConstraints createConstraints(
            int column,
            int row,
            int gridWidth
    ) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.gridwidth = gridWidth;
        return constraints;
    }

    private void startGame() {
        String player1Name = player1NameField.getText().trim();
        String player2Name = player2NameField.getText().trim();

        // FIXME: perform input validation on player names

        dispose();
        new MainView(player1Name, player2Name);
    }
}