import javax.swing.SwingUtilities;
import view.WelcomeView;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeView().setVisible(true));
    }
}
