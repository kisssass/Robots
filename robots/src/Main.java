import view.View;
import viewmodel.ViewModel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            view.setVisible(true);
            view.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
