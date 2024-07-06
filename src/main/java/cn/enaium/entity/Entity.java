package cn.enaium.entity;

import cn.enaium.Aircraft;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import manifold.ext.props.rt.api.val;
import manifold.ext.props.rt.api.var;

import java.util.UUID;

/**
 * @author Enaium
 */
public abstract class Entity {

    public static final double MOVE_UNIT = 20;

    public boolean dead = false;
    public boolean visible = true;
    public boolean living = true;

    @val
    UUID id = UUID.randomUUID();

    @var
    Point2D position = Point2D.ZERO;

    @var
    Point2D origin = Point2D.ZERO;

    public void setOrigin(Point2D origin) {
        this.origin = origin;
        this.position = origin;
    }

    @var
    Rectangle2D bounds;

    public abstract void draw(GraphicsContext gc);

    public abstract void update();

    public double distance(Entity entity) {
        return position.distance(entity.position);
    }

    public boolean isBounded(Entity entity) {
        return bounds.intersects(entity.bounds);
    }

    public void kill() {
        dead = true;
        visible = false;
        living = false;
    }

    public boolean isOutOfScreen() {
        return position.getX() < 0 || position.getX() > Aircraft.inGame.width || position.getY() < 0 || position.getY() > Aircraft.inGame.height;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", class=" + getClass().getSimpleName() +
                ", position=" + position +
                ", origin=" + origin +
                ", bounds=" + bounds +
                '}';
    }
}
