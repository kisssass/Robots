package model;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Model{
    private final List<Entity> entities = new ArrayList<>();
    public Model()
    {
        entities.add(new RobotEntity(100, 100, 0, 150, 100, 0.1, 0.001));
    }
    public void updateModel() {
        for (Entity entity : entities) {
            entity.update();
        }
    }
    public void mouseActionUpdate(Point p) {
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot){
                robot.setM_targetPositionX(p.x);
                robot.setM_targetPositionY(p.y);
            }
        }
    }
    public List<Entity> getEntities() {
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
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot){
                double distance = distance(robot.getTargetPositionX(), robot.getTargetPositionY(),
                        robot.getRobotPositionX(), robot.getRobotPositionY());
                if (distance < 0.5)
                {
                    return;
                }
                velocity = robot.getMaxVelocity();
                angleToTarget = angleTo(robot.getRobotPositionX(), robot.getRobotPositionY(), robot.getTargetPositionX(), robot.getTargetPositionY());
                if (angleToTarget > robot.getRobotDirection())
                {
                    angularVelocity = robot.getMaxAngularVelocity();
                }
                if (angleToTarget < robot.getRobotDirection())
                {
                    angularVelocity = -robot.getMaxAngularVelocity();
                }
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
            if (entity instanceof RobotEntity robot) {
                velocity = applyLimits(velocity, 0, robot.getMaxVelocity());
                angularVelocity = applyLimits(angularVelocity, -robot.getMaxAngularVelocity(), robot.getMaxAngularVelocity());
                double newX = robot.getRobotPositionX() + velocity / angularVelocity *
                        (Math.sin(robot.getRobotDirection()  + angularVelocity * duration) -
                                Math.sin(robot.getRobotDirection()));
                if (!Double.isFinite(newX))
                {
                    newX = robot.getRobotPositionX() + velocity * duration * Math.cos(robot.getRobotDirection());
                }
                double newY = robot.getRobotPositionY() - velocity / angularVelocity *
                        (Math.cos(robot.getRobotDirection()  + angularVelocity * duration) -
                                Math.cos(robot.getRobotDirection()));
                if (!Double.isFinite(newY))
                {
                    newY = robot.getRobotPositionY() + velocity * duration * Math.sin(robot.getRobotDirection());
                }
                robot.setRobotPositionX(newX);
                robot.setRobotPositionY(newY);
                double newDirection = asNormalizedRadians(robot.getRobotDirection() + angularVelocity * duration);
                robot.setRobotDirection(newDirection);
            }
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
