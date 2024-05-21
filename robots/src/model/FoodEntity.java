package model;

import java.awt.*;

public class FoodEntity implements Entity{
    private double m_PositionX;
    private double m_PositionY;
    private Color m_color;
    private int m_Diameter;

    public FoodEntity(int PositionX, int PositionY, Color color, int diameter) {
        this.m_PositionX = PositionX;
        this.m_PositionY = PositionY;
        this.m_color = color;
        this.m_Diameter=diameter;
    }

    public Color getColor() {
        return m_color;
    }
    @Override
    public double getEntityPositionX() {return m_PositionX;}
    @Override
    public double getEntityPositionY() {return m_PositionY;}
    @Override
    public int getEntitySize() {return m_Diameter;}
    @Override
    public void update(ModelContext modelContext) {}
}
