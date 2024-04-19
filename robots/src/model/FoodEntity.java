package model;

import java.awt.*;

public class FoodEntity implements Entity{
    private int m_foodPositionX;
    private int m_foodPositionY;
    private Color m_color;

    public FoodEntity(int foodPositionX, int foodPositionY, Color color) {
        this.m_foodPositionX = foodPositionX;
        this.m_foodPositionY = foodPositionY;
        this.m_color = color;
    }

    public int getFoodPositionX() {
        return m_foodPositionX;
    }
    public void setFoodPositionX(int foodPositionX) {
        this.m_foodPositionX = foodPositionX;
    }
    public int getFoodPositionY() {
        return m_foodPositionY;
    }
    public void setFoodPositionY(int foodPositionY) {
        this.m_foodPositionY = foodPositionY;
    }
    public Color getColor() {
        return m_color;
    }
    public void setColor(Color color) {
        this.m_color = color;
    }
    @Override
    public void update() {

    }
}
