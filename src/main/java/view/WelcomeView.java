package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private static final int LANGUAGE_ROW = 3;
    private static final int START_BUTTON_ROW = 4;
    private static final int COMPONENT_INSET = 10;

    private final List<Locale> supportedLocales;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private JLabel welcomeLabel;
    private JLabel player1NameLabel;
    private JLabel player2NameLabel;
    private JLabel languageLabel;
    private JComboBox<String> languageSelector;
    private JButton startGameButton;

    public WelcomeView() {
        supportedLocales = getSupportedLocales();
        configureWelcomeView();
        createWelcomeScreenUI();
        updateLocalizedText();
    }

    private void configureWelcomeView() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createWelcomeScreenUI() {
        JPanel welcomePanel = createWelcomePanel();

        addWelcomeLabel(welcomePanel);
        player1NameField = addPlayerNameInput(welcomePanel, 1);
        player2NameField = addPlayerNameInput(welcomePanel, 2);
        addLanguageSelector(welcomePanel);
        addStartGameButton(welcomePanel);

        add(welcomePanel);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(BACKGROUND_COLOR);
        return welcomePanel;
    }

    private void addWelcomeLabel(JPanel welcomePanel) {
        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        welcomeLabel.setForeground(Color.WHITE);

        welcomePanel.add(
                welcomeLabel,
                createConstraints(0, 0, 2)
        );
    }

    private JTextField addPlayerNameInput(JPanel welcomePanel, int row) {
        JLabel nameLabel = new JLabel();
        nameLabel.setFont(new Font(FONT_NAME, Font.PLAIN, NAME_FONT_SIZE));
        nameLabel.setForeground(Color.WHITE);

        if (row == 1) {
            player1NameLabel = nameLabel;
        } else {
            player2NameLabel = nameLabel;
        }

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

    private void addLanguageSelector(JPanel welcomePanel) {
        languageLabel = new JLabel();
        languageLabel.setFont(new Font(FONT_NAME, Font.PLAIN, NAME_FONT_SIZE));
        languageLabel.setForeground(Color.WHITE);

        welcomePanel.add(
                languageLabel,
                createConstraints(0, LANGUAGE_ROW, 1)
        );

        String[] languageNames = supportedLocales.stream()
                .map(locale -> locale.getDisplayLanguage(locale))
                .toArray(String[]::new);

        languageSelector = new JComboBox<>(languageNames);
        languageSelector.setFont(new Font(FONT_NAME, Font.PLAIN, NAME_FONT_SIZE));
        languageSelector.addActionListener(event -> updateLocalizedText());

        welcomePanel.add(
                languageSelector,
                createConstraints(1, LANGUAGE_ROW, 1)
        );
    }

    private void addStartGameButton(JPanel welcomePanel) {
        startGameButton = new JButton();
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

    private void updateLocalizedText() {
        ResourceBundle labels = getSelectedLabels();

        setTitle(labels.getString("welcomeWindowTitle"));
        welcomeLabel.setText(labels.getString("welcomeMessage"));
        player1NameLabel.setText(labels.getString("player1NameLabel"));
        player2NameLabel.setText(labels.getString("player2NameLabel"));
        languageLabel.setText(labels.getString("languageLabel"));
        startGameButton.setText(labels.getString("startGameButton"));
    }

    private void startGame() {
        String player1Name = player1NameField.getText().strip();
        String player2Name = player2NameField.getText().strip();

        Optional<String> validationError = validatePlayerNames(player1Name, player2Name);

        if (validationError.isPresent()) {
            showValidationError(validationError.get());
            return;
        }

        Locale selectedLocale = supportedLocales.get(
                languageSelector.getSelectedIndex()
        );

        dispose();
        new MainView(player1Name, player2Name, selectedLocale);
    }

    private Optional<String> validatePlayerNames(String player1Name, String player2Name) {
        ResourceBundle labels = getSelectedLabels();

        if (player1Name.isBlank() && player2Name.isBlank()) {
            return Optional.of(labels.getString("bothNamesRequired"));
        }

        if (player1Name.isBlank()) {
            return Optional.of(labels.getString("player1NameRequired"));
        }

        if (player2Name.isBlank()) {
            return Optional.of(labels.getString("player2NameRequired"));
        }

        if (player1Name.length() > MAX_PLAYER_NAME_LENGTH) {
            return Optional.of(MessageFormat.format(
                    labels.getString("player1NameTooLong"),
                    MAX_PLAYER_NAME_LENGTH
            ));
        }

        if (player2Name.length() > MAX_PLAYER_NAME_LENGTH) {
            return Optional.of(MessageFormat.format(
                    labels.getString("player2NameTooLong"),
                    MAX_PLAYER_NAME_LENGTH
            ));
        }

        return Optional.empty();
    }

    private void showValidationError(String message) {
        ResourceBundle labels = getSelectedLabels();

        JOptionPane.showMessageDialog(
                this,
                message,
                labels.getString("invalidPlayerNameTitle"),
                JOptionPane.ERROR_MESSAGE
        );
    }

    private ResourceBundle getSelectedLabels() {
        Locale locale = supportedLocales.get(
                languageSelector.getSelectedIndex()
        );
        return ResourceBundle.getBundle("labels", locale);
    }

    static List<Locale> getSupportedLocales() {
        ResourceBundle labels = ResourceBundle.getBundle(
                "labels",
                Locale.ENGLISH
        );

        return Arrays.stream(
                        labels.getString("supportedLocales").split(",")
                )
                .map(String::strip)
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }
}