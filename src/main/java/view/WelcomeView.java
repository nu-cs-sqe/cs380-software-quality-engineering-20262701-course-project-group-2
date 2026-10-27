package view;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JFrame {
    private JTextField player1NameField;
    private JTextField player2NameField;

    public WelcomeView() {
        setTitle("Welcome Screen!");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createWelcomeScreenUI();
    }

    private void createWelcomeScreenUI() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(104, 76, 150));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome to our Chess Game!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        welcomePanel.add(welcomeLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel player1NameLabel = new JLabel("Player 1 Name:");
        player1NameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        player1NameLabel.setForeground(Color.WHITE);
        welcomePanel.add(player1NameLabel, constraints);

        player1NameField = new JTextField(20);
        player1NameField.setFont(new Font("Arial", Font.PLAIN, 16));
        constraints.gridx = 1;
        welcomePanel.add(player1NameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel player2NameLabel = new JLabel("Player 2 Name:");
        player2NameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        player2NameLabel.setForeground(Color.WHITE);
        welcomePanel.add(player2NameLabel, constraints);

        player2NameField = new JTextField(20);
        player2NameField.setFont(new Font("Arial", Font.PLAIN, 16));
        constraints.gridx = 1;
        welcomePanel.add(player2NameField, constraints);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        startGameButton.setOpaque(true);
        startGameButton.setBackground(Color.WHITE);
        startGameButton.setForeground(Color.BLACK);
        startGameButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        startGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startGameButton.addActionListener(event -> startGame());

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        welcomePanel.add(startGameButton, constraints);

        add(welcomePanel);
    }

    private void startGame() {
        String player1Name = player1NameField.getText().trim();
        String player2Name = player2NameField.getText().trim();

        // FIXME: perform input validation on player names

        dispose();
        new MainView(player1Name, player2Name);
    }
}
