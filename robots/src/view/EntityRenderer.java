package view;

import model.Entity;

import java.awt.*;

public interface EntityRenderer <E extends Entity>{
    void render(E entity, Graphics2D graphics);
}
