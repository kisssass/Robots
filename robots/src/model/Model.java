package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*Модель (Model): Это слой, который содержит данные и бизнес-логику
приложения. Модель представляет собой объекты, которые представляют
реальные или виртуальные объекты в вашем приложении. Модель не
зависит от пользовательского интерфейса и обрабатывает все операции,
связанные с данными.*/
public class Model extends JPanel {
    private final List<Entity> entities = new ArrayList<>();
    public Model()
    {
        entities.add(new Entity(100, 100, 0, 150, 100, 0.1, 0.001));
    }
    public void updateModel(Point p) {
        for (Entity entity : entities) {
            entity.update(p);
        }
    }
    public List<Entity> getEntities() {
        return entities;
    }

    public void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    public static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
    //ниже может быть ошибка
    public void onModelUpdateEvent()
    {
        double velocity = 0;
        double angleToTarget = 0;
        double angularVelocity = 0;
        for (Entity entity : entities) {
            double distance = distance(entity.getTargetPositionX(), entity.getTargetPositionY(),
                    entity.getRobotPositionX(), entity.getRobotPositionY());
            if (distance < 0.5)
            {
                return;
            }
            velocity = entity.getMaxVelocity();
            angleToTarget = angleTo(entity.getRobotPositionX(), entity.getRobotPositionY(), entity.getTargetPositionX(), entity.getTargetPositionY());
            if (angleToTarget > entity.getRobotDirection())
            {
                angularVelocity = entity.getMaxAngularVelocity();
            }
            if (angleToTarget < entity.getRobotDirection())
            {
                angularVelocity = -entity.getMaxAngularVelocity();
            }
        }

        moveRobot(velocity, angularVelocity, 10);
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        for (Entity entity : entities) {
            velocity = applyLimits(velocity, 0, entity.getMaxVelocity());
            angularVelocity = applyLimits(angularVelocity, -entity.getMaxAngularVelocity(), entity.getMaxAngularVelocity());
            double newX = entity.getRobotPositionX() + velocity / angularVelocity *
                    (Math.sin(entity.getRobotDirection()  + angularVelocity * duration) -
                            Math.sin(entity.getRobotDirection()));
            if (!Double.isFinite(newX))
            {
                newX = entity.getRobotPositionX() + velocity * duration * Math.cos(entity.getRobotDirection());
            }
            double newY = entity.getRobotPositionY() - velocity / angularVelocity *
                    (Math.cos(entity.getRobotDirection()  + angularVelocity * duration) -
                            Math.cos(entity.getRobotDirection()));
            if (!Double.isFinite(newY))
            {
                newY = entity.getRobotPositionY() + velocity * duration * Math.sin(entity.getRobotDirection());
            }
            entity.setRobotPositionX(newX);
            entity.setRobotPositionY(newY);
            double newDirection = asNormalizedRadians(entity.getRobotDirection() + angularVelocity * duration);
            entity.setRobotDirection(newDirection);
        }
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    private static int round(double value)
    {
        return (int)(value + 0.5);
    }

    @Override
    public void paint(Graphics g)
    {
        for (Entity entity : entities) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            drawRobot(g2d, round(entity.getRobotPositionX()), round(entity.getRobotPositionY()), entity.getRobotDirection());
            drawTarget(g2d, entity.getTargetPositionX(), entity.getTargetPositionY());
        }
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
        for (Entity entity : entities) {
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
