package model;

import java.awt.*;

public class RobotEntity implements Entity {
    private double m_robotPositionX;
    private double m_robotPositionY;
    private double m_robotDirection;

    private int m_targetPositionX;
    private int m_targetPositionY;

    private final double maxVelocity;
    private final double maxAngularVelocity;


    public RobotEntity(double robotPositionX, double robotPositionY,
                       double robotDirection, int targetPositionX,
                       int targetPositionY , double Velocity, double AngularVelocity) {
        this.m_robotPositionX = robotPositionX;
        this.m_robotPositionY = robotPositionY;
        this.m_robotDirection = robotDirection;
        this.m_targetPositionX = targetPositionX;
        this.m_targetPositionY = targetPositionY;
        this.maxVelocity = Velocity;
        this.maxAngularVelocity = AngularVelocity;
    }

    public double getRobotPositionX() {return m_robotPositionX;}
    public void setRobotPositionX(double positionX){ this.m_robotPositionX =positionX;}
    public double getRobotPositionY() {
        return m_robotPositionY;
    }
    public void setRobotPositionY(double positionY){ this.m_robotPositionY =positionY;}

    public double getRobotDirection() {
        return m_robotDirection;
    }
    public void setRobotDirection(double direction){ this.m_robotDirection=direction;}

    public int getTargetPositionX() {return m_targetPositionX;}
    public void setM_targetPositionX(int positionX){this.m_targetPositionX = positionX;}
    public int getTargetPositionY() {return m_targetPositionY;
    }
    public void setM_targetPositionY(int positionY){this.m_targetPositionY = positionY;}

    public double getMaxVelocity() {
        return maxVelocity;
    }
    public double getMaxAngularVelocity() {
        return maxAngularVelocity;
    }

    public void update() {}

}
