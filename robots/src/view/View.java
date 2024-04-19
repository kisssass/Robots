package view;

import model.Entity;
import model.FoodEntity;
import model.Model;
import model.RobotEntity;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class View extends JPanel{
    private final Model model;
    private final Map<Class<?>, EntityRenderer<?>> rendererByEntityType = Map.of(RobotEntity.class, new RobotRenderer<>(), FoodEntity.class, new FoodRenderer<>());
    public View(Model model) {
        this.model = model;
    }
    public void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }


    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        for (Entity entity : model.getEntities()) {
            EntityRenderer<Entity> renderer = (EntityRenderer<Entity>) rendererByEntityType.get(entity.getClass());
            renderer.render(entity, g2d);
        }
    }
}
