package view;

import model.RobotEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotRenderer<E extends RobotEntity> implements EntityRenderer<RobotEntity> {
    @Override
    public void render(RobotEntity entity, Graphics2D g2d) {
        drawRobot(g2d, round(entity.getRobotPositionX()), round(entity.getRobotPositionY()),entity.getRobotDiameter(), entity.getRobotDirection(), entity.getColor());
        drawTarget(g2d, entity.getTargetPositionX(), entity.getTargetPositionY());
    }

    private void drawRobot(Graphics2D g, int x, int y, int diameter, double direction, Color color)
    {
            int robotCenterX = round(x);
            int robotCenterY = round(y);
            AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
            g.setTransform(t);
            g.setColor(color);
            fillOval(g, robotCenterX, robotCenterY, diameter, diameter);
            g.setColor(Color.BLACK);
            drawOval(g, robotCenterX, robotCenterY, diameter, diameter);
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
}
