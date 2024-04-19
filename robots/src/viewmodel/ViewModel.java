package viewmodel;
import model.Model;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;


public class ViewModel extends JInternalFrame{
    private final View view;
    private final Model model;

    private static java.util.Timer initTimer()
    {
        java.util.Timer timer = new Timer("events generator", true);
        return timer;
    }
    public ViewModel()
    {
        super("Игровое поле", true, true, true, true);
        model = new Model();
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
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                model.onModelUpdateEvent();
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
                model.eatFood();
            }
        }, 0, 50);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                model.mouseActionUpdate(e.getPoint());
                repaint();
            }
        });
    }
}
