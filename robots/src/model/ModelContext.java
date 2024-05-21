package model;

public class ModelContext {
    private final Model model;

    public ModelContext(Model model) {
        this.model = model;
    }

    public void eatEntities (Entity eater){
        double eaterX = eater.getEntityPositionX();
        double eaterY = eater.getEntityPositionY();
        if(eater instanceof RobotEntity robot){
            model.eating(robot, eaterX, eaterY);
        }
    }

    public void moveRobot (RobotEntity mover){
        if (mover.getMoveLeft()) mover.setPositionX(mover.getEntityPositionX()-2);
        if (mover.getMoveRight()) mover.setPositionX(mover.getEntityPositionX()+2);
        if (mover.getMoveUp()) mover.setPositionY(mover.getEntityPositionY()-2);
        if (mover.getMoveDown()) mover.setPositionY(mover.getEntityPositionY()+2);
        model.sortByRobotSize();
        model.coupling(mover);
    }
}
