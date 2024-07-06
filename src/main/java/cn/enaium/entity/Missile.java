package cn.enaium.entity;

import cn.enaium.Aircraft;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * @author Enaium
 */
public class Missile extends RemovableEntity {

    private static final int SIZE = 10;

    public final Point2D from;
    public final Point2D to;

    public Missile(Point2D from, Point2D bomber) {
        super("", Color.MEDIUMSPRINGGREEN, SIZE);
        this.from = from;
        to = bomber;
    }

    @Nullable
    Double angle;

    @Override
    public void update() {
        if (angle == null) {
            angle = Math.atan2(to.getY() - position.getY(), to.getX() - position.getX());
        }

        move(angle, new Random().nextInt(2, 8));

        Aircraft.inGame.entities.stream()
                .filter(entity -> entity instanceof Bomber)
                .map(entity -> (Bomber) entity)
                .filter(entity -> entity.target.isNotNull())
                .filter(entity -> entity.living && entity.visible && !entity.dead)
                .forEach(it -> {
                    if (isBounded(it)) {
                        it.kill();
                        kill();
                    }
                });

        if (isOutOfScreen()) {
            kill();
        }
    }
}
