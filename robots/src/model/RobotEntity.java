package model;

import java.awt.*;

public class RobotEntity implements Entity {
    private int player;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private int m_robotDiameter;
    private double m_robotPositionX;
    private double m_robotPositionY;
    private Color m_color;


    public RobotEntity(int playerNumber, boolean isMoveLeft, boolean isMoveRight, boolean isMoveUp, boolean isMoveDown, Color color, int robotDiameter, double robotPositionX, double robotPositionY) {
        this.player = playerNumber;
        this.moveLeft = isMoveLeft;
        this.moveRight = isMoveRight;
        this.moveUp = isMoveUp;
        this.moveDown = isMoveDown;
        this.m_color = color;
        this.m_robotDiameter = robotDiameter;
        this.m_robotPositionX = robotPositionX;
        this.m_robotPositionY = robotPositionY;
    }

    public Color getColor() {
        return m_color;
    }
    public int getPlayer(){return player;}
    public void setPlayer(int number){this.player = number;}

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
    public double getRobotPositionX() {return m_robotPositionX;}
    public void setRobotPositionX(double positionX){ this.m_robotPositionX =positionX;}
    public double getRobotPositionY() {
        return m_robotPositionY;
    }
    public void setRobotPositionY(double positionY){ this.m_robotPositionY =positionY;}
    public void update() {}

}
