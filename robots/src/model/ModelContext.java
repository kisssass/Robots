package model;

public class ModelContext {
    private final Model model;

    public ModelContext(Model model) {
        this.model = model;
    }
    public void eatFood (RobotEntity eater){
        double robotX = eater.getRobotPositionX();
        double robotY = eater.getRobotPositionY();
        double robotDiameter = eater.getRobotDiameter();
        if(model.isSamePosition(robotX, robotY, robotDiameter)){
            eater.setRobotDiameter(eater.getRobotDiameter()+10);
        }
    }
    public void moveRobot (RobotEntity mover){
        if (mover.getMoveLeft()) mover.setRobotPositionX(mover.getRobotPositionX()-2);
        if (mover.getMoveRight()) mover.setRobotPositionX(mover.getRobotPositionX()+2);
        if (mover.getMoveUp()) mover.setRobotPositionY(mover.getRobotPositionY()-2);
        if (mover.getMoveDown()) mover.setRobotPositionY(mover.getRobotPositionY()+2);
        model.robotSize();
    }
}
