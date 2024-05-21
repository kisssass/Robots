package model;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Integer.parseInt;


public class Model implements Comparator<Entity>{
    private final List<Entity> entities = new CopyOnWriteArrayList<>();
    private final ModelContext modelContext;
    private final int width;
    private final int height;

    Map<String, Integer> keys = new HashMap<>();

    public Model(int numberOfPlayers, int widthOfScreen, int heightOfScreen)
    {
        this.width = widthOfScreen;
        this.height = heightOfScreen;
        modelContext = new ModelContext(this);
        for(int i = 1; i<=numberOfPlayers; i++){
            addRobots(i);
        }
        keyboard();
    }
    public List<Entity> getEntities() {
        return entities;
    }
    public void updateModel() {
        for (Entity entity : entities) {
            entity.update(modelContext);
        }
    }
    public void keyboard(){
        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream("robots/src/resources/game.properties"));
            int count  = 1;
            int size = properties.size();
            while (size!=0){
                String k = count + "Player" + "_SplitKey";
                String a = count + "Player" + "_UpKey";
                String b = count + "Player" + "_DownKey";
                String c = count + "Player" + "_LeftKey";
                String d = count + "Player" + "_RightKey";

                keys.put(k, parseInt(properties.getProperty(k)));
                keys.put(a, parseInt(properties.getProperty(a)));
                keys.put(b, parseInt(properties.getProperty(b)));
                keys.put(c, parseInt(properties.getProperty(c)));
                keys.put(d, parseInt(properties.getProperty(d)));
                size-=5;
                count+=1;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void addRobots(int playerNumber){
        entities.add(new RobotEntity(playerNumber,false, false, false,false, chooseColor(), 30,rndCords (width), rndCords(height)));
    }
    public void addFood(){
        entities.add(new FoodEntity(rndCords(width), rndCords(height), chooseColor(), 10));
    }

    /*берётся значение клавиши из мап и не зависит от кол-ва роботов*/
    /*swich было бы славно, но кейсы не могут содержать поля к сожалению The case labels must be compile-time evaluable constant expressions*/
    public void keyActionUpdate(KeyEvent e, String action) {
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robot){
                if(action.equals("pressed")){
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_LeftKey")) robot.setMoveLeft(true);
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_RightKey")) robot.setMoveRight(true);
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_UpKey")) robot.setMoveUp(true);
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_DownKey")) robot.setMoveDown(true);
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_SplitKey")) split(robot);
                }
                if(action.equals("realeased")){
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_LeftKey")) robot.setMoveLeft(false);
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_RightKey")) robot.setMoveRight(false);
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_UpKey")) robot.setMoveUp(false);
                    if (e.getKeyCode()==keys.get(robot.getPlayer()+"Player" + "_DownKey")) robot.setMoveDown(false);
                }
            }
        }
    }
    public static int rndCords(int max)
    {
        return (int)(Math.random() * ++max);
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
    @Override
    public int compare(Entity o1, Entity o2) {
        return o1.getEntitySize() - o2.getEntitySize();
    }
    public void sortByRobotSize(){
        entities.sort(this);
    }

    public void eating(RobotEntity robotEater, double robotX, double robotY){
        double diameterRobotEater = robotEater.getRobotDiameter();
        for (Entity entity : entities) {
            switch (entity){
                case RobotEntity robot when robotEater!=robot -> {
                    if (robotEater.getRobotDiameter() > robot.getRobotDiameter() || robot.getPlayer()==robotEater.getPlayer()) {
                        if ((Math.abs(robotX - robot.getEntityPositionX()) <= diameterRobotEater/2) && (Math.abs(robotY - robot.getEntityPositionY()) <= diameterRobotEater/2)) {
                            int diam = robot.getRobotDiameter();
                            robotEater.setRobotDiameter(robotEater.getRobotDiameter()+diam);
                            entities.remove(robot);
                        }
                    }
                }
                case FoodEntity food -> {
                    if((Math.abs(food.getEntityPositionX() - robotX )<=diameterRobotEater/2) && (Math.abs(food.getEntityPositionY() - robotY)<= diameterRobotEater/2)){
                        entities.remove(food);
                        robotEater.setRobotDiameter(robotEater.getRobotDiameter()+10);
                    }
                }
                default -> {}
            }
        }
    }

    public void split(RobotEntity robot){
        if(robot.getRobotDiameter()>15){
            int newDiameter = robot.getRobotDiameter()/2;
            double newX = robot.getEntityPositionX();
            double newY = robot.getEntityPositionY();
            if (robot.getMoveLeft()) newX=robot.getEntityPositionX()-newDiameter;
            if (robot.getMoveRight()) newX=robot.getEntityPositionX()+newDiameter;
            if (robot.getMoveUp()) newY= robot.getEntityPositionY()-newDiameter;
            if (robot.getMoveDown()) newY= robot.getEntityPositionY()+newDiameter;
            if (robot.getMoveDown() == robot.getMoveRight() == robot.getMoveUp()== robot.getMoveLeft()) newX=robot.getEntityPositionX()-newDiameter;
            robot.setRobotDiameter(newDiameter);
            entities.add(new RobotEntity(robot.getPlayer(),robot.getMoveLeft(),robot.getMoveRight(),robot.getMoveUp(), robot.getMoveDown(),
                    robot.getColor(),newDiameter,newX,newY));
        }
    }
    public void coupling(RobotEntity robotParent){
        for (Entity entity : entities) {
            if (entity instanceof RobotEntity robotChild && robotParent.getPlayer()==robotChild.getPlayer() && robotChild!=robotParent) {
                double angleToTarget= angleTo(robotChild.getEntityPositionX(), robotChild.getEntityPositionY(), robotParent.getEntityPositionX(), robotParent.getEntityPositionY());
                double newX = robotChild.getEntityPositionX() + 0.1 * Math.cos(angleToTarget) ;
                double newY = robotChild.getEntityPositionY() + 0.1 * Math.sin(angleToTarget) ;
                robotChild.setPositionX(newX);
                robotChild.setPositionY(newY);
            }
        }
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }
}
