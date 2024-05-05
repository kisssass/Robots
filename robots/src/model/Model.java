package model;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


public class Model{
    private List<Entity> entities = new CopyOnWriteArrayList<>();
    public Model()
    {
        entities.add(new RobotEntity(1,false, false, false,false, chooseColor(), 30,300, 300));
        entities.add(new RobotEntity(2,false, false, false,false, chooseColor(), 30,200, 200));
        entities.add(new RobotEntity(3,false, false, false,false, chooseColor(), 30,100, 100));
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
    public void keyActionUpdate(KeyEvent e, String action) {
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot){
                if(action.equals("pressed") ){
                    if(robot.getPlayer()==1){
                        if (e.getKeyCode()==KeyEvent.VK_LEFT) robot.setMoveLeft(true);
                        if (e.getKeyCode()==KeyEvent.VK_RIGHT) robot.setMoveRight(true);
                        if (e.getKeyCode()==KeyEvent.VK_UP) robot.setMoveUp(true);
                        if (e.getKeyCode()==KeyEvent.VK_DOWN) robot.setMoveDown(true);
                    }
                    if(robot.getPlayer()==2){
                        if (e.getKeyCode()==KeyEvent.VK_A) robot.setMoveLeft(true);
                        if (e.getKeyCode()==KeyEvent.VK_D) robot.setMoveRight(true);
                        if (e.getKeyCode()==KeyEvent.VK_W) robot.setMoveUp(true);
                        if (e.getKeyCode()==KeyEvent.VK_S) robot.setMoveDown(true);
                    }
                    if(robot.getPlayer()==3){
                        if (e.getKeyCode()==KeyEvent.VK_H) robot.setMoveLeft(true);
                        if (e.getKeyCode()==KeyEvent.VK_K) robot.setMoveRight(true);
                        if (e.getKeyCode()==KeyEvent.VK_U) robot.setMoveUp(true);
                        if (e.getKeyCode()==KeyEvent.VK_J) robot.setMoveDown(true);
                    }
                }
                if(action.equals("realeased") ){
                    if(robot.getPlayer()==1){
                        if (e.getKeyCode()==KeyEvent.VK_LEFT) robot.setMoveLeft(false);
                        if (e.getKeyCode()==KeyEvent.VK_RIGHT) robot.setMoveRight(false);
                        if (e.getKeyCode()==KeyEvent.VK_UP) robot.setMoveUp(false);
                        if (e.getKeyCode()==KeyEvent.VK_DOWN) robot.setMoveDown(false);
                    }
                    if(robot.getPlayer()==2){
                        if (e.getKeyCode()==KeyEvent.VK_A) robot.setMoveLeft(false);
                        if (e.getKeyCode()==KeyEvent.VK_D) robot.setMoveRight(false);
                        if (e.getKeyCode()==KeyEvent.VK_W) robot.setMoveUp(false);
                        if (e.getKeyCode()==KeyEvent.VK_S) robot.setMoveDown(false);
                    }
                    if(robot.getPlayer()==3){
                        if (e.getKeyCode()==KeyEvent.VK_H) robot.setMoveLeft(false);
                        if (e.getKeyCode()==KeyEvent.VK_K) robot.setMoveRight(false);
                        if (e.getKeyCode()==KeyEvent.VK_U) robot.setMoveUp(false);
                        if (e.getKeyCode()==KeyEvent.VK_J) robot.setMoveDown(false);
                    }
                }
            }
        }
    }
    public void onModelUpdateEvent()
    {
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot){
                if (robot.getMoveLeft()) robot.setRobotPositionX(robot.getRobotPositionX()-2);
                if (robot.getMoveRight()) robot.setRobotPositionX(robot.getRobotPositionX()+2);
                if (robot.getMoveUp()) robot.setRobotPositionY(robot.getRobotPositionY()-2);
                if (robot.getMoveDown()) robot.setRobotPositionY(robot.getRobotPositionY()+2);
            }
        }
        robotSize();
    }
    public void robotSize(){
        int size1=0;
        int index1=0;
        int size2=0;
        int index2=0;
        int size3=0;
        int index3=0;
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot){
                if(robot.getPlayer()==1){
                    size1 = robot.getRobotDiameter();
                    index1 = entities.indexOf(robot);
                };
                if(robot.getPlayer()==2){
                    size2 = robot.getRobotDiameter();
                    index2 = entities.indexOf(robot);
                }
                if(robot.getPlayer()==3){
                    size3 = robot.getRobotDiameter();
                    index3 = entities.indexOf(robot);
                }
            }
        }
        if (size1>size2 && index1<index2){
            Collections.swap(entities,index1,index2);
        };
        if (size2>size3 && index2<index3){
            Collections.swap(entities,index2,index3);
        };
        if (size1>size3 && index1<index3){
            Collections.swap(entities,index1,index3);
        };
        if (size2>size1 && index2<index1){
            Collections.swap(entities,index2,index1);
        };
        if (size3>size2 && index3<index2){
            Collections.swap(entities,index3,index2);
        };
        if (size3>size1 && index3<index1){
            Collections.swap(entities,index3,index1);
        };
    }

    public void eatFood(){
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot) {
                double robotX = robot.getRobotPositionX();
                double robotY = robot.getRobotPositionY();
                double robotDiameter = robot.getRobotDiameter();
                if(isSamePosition(robotX, robotY, robotDiameter)){
                    robot.setRobotDiameter(robot.getRobotDiameter()+10);
                }
            }
        }
    }
    public void eatRobots(){
        double robotX1=0;
        double robotY1=0;
        int robotDiameter1=0;
        int position1=0;
        double robotX2=0;
        double robotY2=0;
        int robotDiameter2=0;
        int position2=0;
        double robotX3=0;
        double robotY3=0;
        int robotDiameter3=0;
        int position3=0;

        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot) {
                if(robot.getPlayer()==1){
                    robotX1 = robot.getRobotPositionX();
                    robotY1 = robot.getRobotPositionY();
                    robotDiameter1 = robot.getRobotDiameter();
                    position1 = entities.indexOf(robot);
                };
                if(robot.getPlayer()==2){
                    robotX2 = robot.getRobotPositionX();
                    robotY2 = robot.getRobotPositionY();
                    robotDiameter2 = robot.getRobotDiameter();
                    position2 =entities.indexOf(robot);
                }
                if(robot.getPlayer()==3){
                    robotX3 = robot.getRobotPositionX();
                    robotY3 = robot.getRobotPositionY();
                    robotDiameter3 = robot.getRobotDiameter();
                    position3 =entities.indexOf(robot);
                }
            }
        }
        eating(robotX1, robotY1, robotDiameter1,position1,robotX2, robotY2, robotDiameter2,position2,robotX3, robotY3, robotDiameter3,position3);
    }
    private void eating(double X1, double Y1,int Diam1, int index1, double X2, double Y2, int Diam2,int index2, double X3, double Y3, int Diam3, int index3){
        int index=0;
        int diam=0;
        if(Diam2>0){
            if (Diam1 > Diam2) {
                if ((Math.abs(X1 - X2) <= Diam1/2) && (Math.abs(Y1 - Y2) <= Diam1/2)) {
                    entities.remove(index2);
                    index = 1;
                    diam = Diam2;
                }
            }
            if (Diam3 > Diam2) {
                if ((Math.abs(X3 - X2) <= Diam3/2) && (Math.abs(Y3 - Y2) <= Diam3/2)) {
                    entities.remove(index2);
                    index = 3;
                    diam = Diam2;
                }
            }
        }
        if(Diam3>0){
            if (Diam1 > Diam3) {
                if ((Math.abs(X1 - X3) <= Diam1/2) && (Math.abs(Y1 - Y3) <= Diam1/2)) {
                    entities.remove(index3);
                    index = 1;
                    diam = Diam3;
                }
            }
            if (Diam2 > Diam3) {
                if ((Math.abs(X2 - X3) <= Diam2/2) && (Math.abs(Y2 - Y3) <= Diam2/2)) {
                    entities.remove(index3);
                    index = 2;
                    diam = Diam3;
                }
            }
        }
        if(Diam1>0){
            if (Diam2 > Diam1) {
                if ((Math.abs(X2 - X1) <= Diam2/2) && (Math.abs(Y2 - Y1) <= Diam2/2)) {
                    entities.remove(index1);
                    index = 2;
                    diam = Diam1;
                }
            }
            if (Diam3 > Diam1) {
                if ((Math.abs(X3 - X1) <= Diam3/2) && (Math.abs(Y3 - Y1) <= Diam3/2)) {
                    entities.remove(index1);
                    index = 3;
                    diam = Diam1;
                }
            }
        }
        if(diam>0){
            for (Entity entity : entities) {
                if (entity instanceof RobotEntity robot) {
                    if(robot.getPlayer()==index){
                        robot.setRobotDiameter(robot.getRobotDiameter()+diam);
                    }
                }
            }
        }
    };

    private boolean isSamePosition(double robotX, double robotY, double robotDiameter){
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
