package view;

import viewmodel.ViewModel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{
    private final JDesktopPane desktopPane = new JDesktopPane();
    public GameWindow() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);

        ViewModel viewModel = new ViewModel();
        viewModel.setSize(400,  400);
        addWindow(viewModel);
        viewModel.setFocusable(true);
        viewModel.requestFocus();
        viewModel.requestFocusInWindow();
    }
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}