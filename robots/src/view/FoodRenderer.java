package view;
import model.FoodEntity;

import java.awt.*;


public class FoodRenderer <E extends FoodRenderer> implements EntityRenderer<FoodEntity> {
    @Override
    public void render(FoodEntity entity, Graphics2D g2d) {
        drawFood(g2d, round(entity.getEntityPositionX()), round(entity.getEntityPositionY()), entity.getColor(), entity.getEntitySize());
    }
    private void drawFood(Graphics2D g, int x, int y, Color color, int diameter)
    {
        g.setColor(color);
        fillOval(g, x, y, diameter, diameter);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, diameter, diameter);
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
