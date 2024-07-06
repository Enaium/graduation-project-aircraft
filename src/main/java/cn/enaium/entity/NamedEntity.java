package cn.enaium.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import manifold.ext.props.rt.api.val;

/**
 * @author Enaium
 */
public abstract class NamedEntity extends Entity {
    @val
    String name;
    @val
    Color color;

    @val
    int size;

    public NamedEntity(String name, Color color, int size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        bounds = new Rectangle2D(position.x, position.y, size, size);
        gc.drawOval(bounds, color);
        gc.drawText(name, bounds, Color.WHITE);
    }
}
