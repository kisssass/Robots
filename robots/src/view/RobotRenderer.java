package view;

import model.RobotEntity;

import java.awt.*;

public class RobotRenderer<E extends RobotEntity> implements EntityRenderer<RobotEntity> {
    @Override
    public void render(RobotEntity entity, Graphics2D g2d) {
        drawRobot(g2d, round(entity.getEntityPositionX()), round(entity.getEntityPositionY()),entity.getRobotDiameter(), entity.getColor());
    }

    private void drawRobot(Graphics2D g, int x, int y, int diameter, Color color)
    {
            int robotCenterX = round(x);
            int robotCenterY = round(y);
            g.setColor(color);
            fillOval(g, robotCenterX, robotCenterY, diameter, diameter);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(4f));
            drawOval(g, robotCenterX, robotCenterY, diameter, diameter);
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
