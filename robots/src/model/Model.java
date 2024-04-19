package model;
import java.awt.*;
import java.util.List;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


public class Model{
    private List<Entity> entities = new CopyOnWriteArrayList<>();
    //List<Entity> entities = Collections.synchronizedList(synchronizedList);
    public Model()
    {
        entities.add(new RobotEntity(chooseColor(), 30,100, 100, 0, 150, 100, 0.1, 0.001));
    }
    public List<Entity> getEntities() {
        return entities;
    }
    public void updateModel() {
        for (Entity entity : entities) {
            entity.update();
        }
    }
    public static int rnd(int min, int max)
    {
        max -= min;
        return (int)(Math.random() * ++max) + min;
    }
    public void addFood(){
        final int min = 0;
        final int maxX = 1900;
        final int maxY = 950;
        final int rnd1 = rnd(min, maxX);
        final int rnd2 = rnd(min, maxY);
        final Color color = chooseColor();
        entities.add(new FoodEntity(rnd1, rnd2, color));
    }
    private static Color chooseColor()
    {
        Random rand = new Random();
        double r = rand.nextFloat() / 2f + 0.5;
        double g = rand.nextFloat() / 2f + 0.5;
        double b = rand.nextFloat() / 2f + 0.5;
        Color color = new Color((float) r,(float)g,(float)b);
        color.darker();
        return color;
    }
    public void mouseActionUpdate(Point p) {
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot){
                robot.setM_targetPositionX(p.x);
                robot.setM_targetPositionY(p.y);
            }
        }
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
                robot.setRobotDirection(angleToTarget);
            }
        }
        moveRobot(velocity, angularVelocity, 10, angleToTarget);
    }
    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
    private void moveRobot(double velocity, double angularVelocity, double duration, double angleTotarget)
    {
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot) {
                velocity = applyLimits(velocity, 0, robot.getMaxVelocity());
                angularVelocity = applyLimits(angularVelocity, -robot.getMaxAngularVelocity(), robot.getMaxAngularVelocity());
                double newX = robot.getRobotPositionX() + velocity * Math.cos(angleTotarget) * duration;
                double newY = robot.getRobotPositionY() + velocity * Math.sin(angleTotarget) * duration;
                robot.setRobotPositionX(newX);
                robot.setRobotPositionY(newY);
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
    public void eatFood(){
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot) {
                double robotX = robot.getRobotPositionX();
                double robotY = robot.getRobotPositionY();
                double robotDiameter = robot.getRobotDiameter();
                if(isTrue(robotX, robotY, robotDiameter)){
                    robot.setRobotDiameter(robot.getRobotDiameter()+10);
                }
            }
        }
    }
    private boolean isTrue(double robotX, double robotY, double robotDiameter){
        for (Entity entity : entities) {
            if (entity instanceof FoodEntity food){
                if((Math.abs(food.getFoodPositionX() - robotX )<= robotDiameter/2) && (Math.abs(food.getFoodPositionY() - robotY)<= robotDiameter/2)){
                    entities.remove(food);
                    return true;
                }
            }
        }
        return false;
    }
}
