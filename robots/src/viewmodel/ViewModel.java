package viewmodel;

import model.Entity;
import model.Model;
import view.EntityRenderer;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/*ViewModel: Это прослойка между Моделью и Представлением,
которая связывает их между собой. ViewModel содержит логику
представления данных для Представления и обрабатывает взаимодействие
пользователя с интерфейсом. ViewModel обновляет Представление при
изменении данных в Модели и обрабатывает события, такие как нажатия
кнопок или изменения текста.*/
public class ViewModel extends JInternalFrame{
    private final Model model;

    /*private Map<Class<?>, EntityRenderer<?>> renderes = Map.of(
            Entity.class, new View()
    );*/
    private static java.util.Timer initTimer()
    {
        java.util.Timer timer = new Timer("events generator", true);
        return timer;
    }
    public ViewModel()
    {
        super("Игровое поле", true, true, true, true);
        model = new Model();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(model, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        final java.util.Timer m_timer = initTimer();
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                model.onRedrawEvent();
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

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                model.updateModel(e.getPoint());
                repaint();
            }
        });
    }
}
