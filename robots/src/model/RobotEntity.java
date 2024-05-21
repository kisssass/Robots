package model;

import java.awt.*;

import static java.lang.Integer.parseInt;

public class RobotEntity implements Entity{
    private int player;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private int m_robotDiameter;
    private double m_PositionX;
    private double m_PositionY;
    private Color m_color;


    public RobotEntity(int playerNumber, boolean isMoveLeft, boolean isMoveRight, boolean isMoveUp, boolean isMoveDown, Color color,
                       int robotDiameter, double PositionX, double PositionY) {
        this.player = playerNumber;
        this.moveLeft = isMoveLeft;
        this.moveRight = isMoveRight;
        this.moveUp = isMoveUp;
        this.moveDown = isMoveDown;
        this.m_color = color;
        this.m_robotDiameter = robotDiameter;
        this.m_PositionX = PositionX;
        this.m_PositionY = PositionY;
    }

    public Color getColor() {
        return m_color;
    }
    public int getPlayer(){return player;}
    public boolean getMoveLeft() {return moveLeft;}
    public void setMoveLeft(boolean left){ this.moveLeft =left;}
    public boolean getMoveRight() {return moveRight;}
    public void setMoveRight(boolean right){ this.moveRight =right;}
    public boolean getMoveUp() {return moveUp;}
    public void setMoveUp(boolean up){ this.moveUp =up;}
    public boolean getMoveDown() {return moveDown;}
    public void setMoveDown(boolean down){ this.moveDown =down;}
    public void setColor(Color color) {
        this.m_color = color;
    }
    public int getRobotDiameter() {return m_robotDiameter;}
    public void setRobotDiameter(int diameter){ this.m_robotDiameter =diameter;}
    public void setPositionX(double positionX){ this.m_PositionX =positionX;}
    public void setPositionY(double positionY){ this.m_PositionY =positionY;}
    @Override
    public double getEntityPositionX() {return m_PositionX;}
    @Override
    public double getEntityPositionY() {return m_PositionY;}
    @Override
    public int getEntitySize() {return m_robotDiameter;}

    @Override
    public void update(ModelContext modelContext) {
        modelContext.moveRobot(this);
        modelContext.eatEntities(this);
    }
}
