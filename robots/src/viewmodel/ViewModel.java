package viewmodel;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/*ViewModel: Это прослойка между Моделью и Представлением,
которая связывает их между собой. ViewModel содержит логику
представления данных для Представления и обрабатывает взаимодействие
пользователя с интерфейсом. ViewModel обновляет Представление при
изменении данных в Модели и обрабатывает события, такие как нажатия
кнопок или изменения текста.*/
public class ViewModel extends JInternalFrame{
    private final View view;

    private static java.util.Timer initTimer()
    {
        java.util.Timer timer = new Timer("events generator", true);
        return timer;
    }

    public ViewModel()
    {
        super("Игровое поле", true, true, true, true);
        view = new View();
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
                view.getModel().onModelUpdateEvent();
            }
        }, 0, 10);
    }
}
