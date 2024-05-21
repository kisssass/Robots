package viewmodel;
import model.Model;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class ViewModel extends JInternalFrame{
    private final View view;
    private final Model model;
    private final static int numberOfPlayers = 3;


    private static java.util.Timer initTimer()
    {
        java.util.Timer timer = new Timer("events generator", true);
        return timer;
    }
    public ViewModel(int height, int width)
    {
        super("Игровое поле", true, true, true, true);
        model = new Model(numberOfPlayers, width, height);
        view = new View(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(view, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        final java.util.Timer m_timer = initTimer();
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                view.onRedrawEvent();
            }
        }, 0, 10);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                model.addFood();
            }
        }, 0, 1000);

        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                model.updateModel();
            }
        }, 0, 10);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                model.keyActionUpdate(e,"pressed");
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                model.keyActionUpdate(e, "realeased");
                repaint();
            }
        });
    }
}
