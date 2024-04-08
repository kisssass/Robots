package model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/*Модель (Model): Это слой, который содержит данные и бизнес-логику
приложения. Модель представляет собой объекты, которые представляют
реальные или виртуальные объекты в вашем приложении. Модель не
зависит от пользовательского интерфейса и обрабатывает все операции,
связанные с данными.*/
public class Model{
    private final List<RobotEntity> entities = new ArrayList<>();
    public Model()
    {
        entities.add(new RobotEntity(100, 100, 0, 150, 100, 0.1, 0.001));
    }
    public void updateModel() {
        for (RobotEntity entity : entities) {
            entity.update();
        }
    }
    public void mouseActionUpdate(Point p) {
        for (RobotEntity entity : entities) {
            entity.setM_targetPositionX(p.x);
            entity.setM_targetPositionY(p.y);
        }
    }
    public List<RobotEntity> getEntities() {
        return entities;
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
    public void onModelUpdateEvent()
    {
        double velocity = 0;
        double angleToTarget = 0;
        double angularVelocity = 0;
        for (RobotEntity entity : entities) {
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
        for (RobotEntity entity : entities) {
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
}
