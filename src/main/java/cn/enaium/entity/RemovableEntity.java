package cn.enaium.entity;

import cn.enaium.Aircraft;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.Level;

import static cn.enaium.Aircraft.LOGGER;

/**
 * @author Enaium
 */
public abstract class RemovableEntity extends NamedEntity {
    public RemovableEntity(String name, Color color, int size) {
        super(name, color, size);
    }

    /**
     * 移动
     *
     * @param target 目标
     * @param unit   单位
     */
    public void move(Point2D target, int unit) {
        double angle = Math.atan2(target.getY() - position.getY(), target.getX() - position.getX());
        move(angle, unit);
    }

    /**
     * 移动且需经过每个点
     *
     * @param angle 角度
     * @param unit  单位
     */
    public void move(double angle, int unit) {
        position += new Point2D(Math.cos(angle), Math.sin(angle)) * unit * MOVE_UNIT / (Aircraft.inGame.fps != 0 ? Aircraft.inGame.fps : 1);
    }
}
