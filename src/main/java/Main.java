import view.WelcomeView;

import javax.swing.*;

public class Main{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            new WelcomeView().setVisible(true);
        });
    }

}