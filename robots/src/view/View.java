package view;

import model.Entity;
import model.Model;
import viewmodel.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;


/* Представление (View): Это слой, который отображает данные
пользователю и обрабатывает пользовательский ввод. Представление
представляет собой интерфейс пользователя, такой как окна, кнопки,
текстовые поля и т. д. Представление не содержит
бизнес-логику и должно быть максимально декларативным.*/
public class View extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    public View() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);

        ViewModel viewModel = new ViewModel();
        viewModel.setSize(400,  400);
        addWindow(viewModel);
    }
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
