package view;

import model.RobotEntity;

import java.awt.*;

public interface EntityRenderer <E extends RobotEntity>{
    void render(E entity, Graphics graphics);
}
