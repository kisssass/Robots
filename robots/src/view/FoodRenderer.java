package view;
import model.FoodEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class FoodRenderer <E extends FoodRenderer> implements EntityRenderer<FoodEntity> {
    @Override
    public void render(FoodEntity entity, Graphics2D g2d) {
        drawFood(g2d, entity.getFoodPositionX(), entity.getFoodPositionY(), entity.getColor());
    }
    private void drawFood(Graphics2D g, int x, int y, Color color)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(color);
        fillOval(g, x, y, 10, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 10, 10);
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
