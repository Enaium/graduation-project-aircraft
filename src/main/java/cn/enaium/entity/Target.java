package cn.enaium.entity;

import javafx.scene.paint.Color;

/**
 * @author Enaium
 */
public class Target extends NamedEntity {

    public static final int SIZE = 50;

    public Target() {
        super("T", Color.TURQUOISE, SIZE);
    }

    @Override
    public void update() {

    }
}
