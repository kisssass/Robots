package view;

import model.Model;
import model.RobotEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;



/* Представление (View): Это слой, который отображает данные
пользователю и обрабатывает пользовательский ввод. Представление
представляет собой интерфейс пользователя, такой как окна, кнопки,
текстовые поля и т. д. Представление не содержит
бизнес-логику и должно быть максимально декларативным.*/
public class View extends JPanel{
    private final Model model;
    public View() {
        this.model = new Model();
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
    public Model getModel(){
        return this.model;
    }
    public void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g)
    {
        for (RobotEntity entity : model.getEntities()) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            drawRobot(g2d, round(entity.getRobotPositionX()), round(entity.getRobotPositionY()), entity.getRobotDirection());
            drawTarget(g2d, entity.getTargetPositionX(), entity.getTargetPositionY());
        }
    }
    private static int round(double value)
    {
        return (int)(value + 0.5);
    }
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {
        for (RobotEntity entity : model.getEntities()) {
            int robotCenterX = round(entity.getRobotPositionX());
            int robotCenterY = round(entity.getRobotPositionY());
            AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
            g.setTransform(t);
            g.setColor(Color.MAGENTA);
            fillOval(g, robotCenterX, robotCenterY, 30, 10);
            g.setColor(Color.BLACK);
            drawOval(g, robotCenterX, robotCenterY, 30, 10);
            g.setColor(Color.WHITE);
            fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
            g.setColor(Color.BLACK);
            drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        }
    }
    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
