package cn.enaium;

import cn.enaium.entity.Base;
import cn.enaium.entity.Bomber;
import cn.enaium.entity.Entity;
import cn.enaium.entity.Target;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import manifold.ext.props.rt.api.val;
import manifold.ext.props.rt.api.var;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static cn.enaium.Aircraft.LOGGER;

/**
 * @author Enaium
 */
public class InGame {

    @val
    public List<Entity> entities = new CopyOnWriteArrayList<>();
    public int fps;

    @var
    double width;

    @var
    double height;

    private final Set<KeyCode> pressedKeys = new HashSet<>();

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        entities.stream().filter(it -> it.visible).forEach(entity -> entity.draw(gc));
    }

    public void update() {
        entities.stream().filter(it -> it.living).forEach(Entity::update);
        Aircraft.timer.use(() -> {
            LOGGER.log(Level.INFO, "Entities:{}", entities);
        }, 1000);
    }

    public void generateBomber() {
        final var random = new Random();
        // 生成3批轰炸机
        for (int i = 0; i < 3; i++) {
            // 每批生成2-8架轰炸机
            for (int j = 0; j < random.nextInt(2, 8); j++) {
                entities.add(new Bomber().apply(bomber -> {
                    bomber.origin = new Point2D(random.nextDouble(0, width / 3f), random.nextDouble(0, height - Bomber.SIZE));
                }));
            }
        }
    }

    public void createBase() {
        final var random = new Random();
        entities.addAll(Arrays.asList(new Base().apply(base -> base.position = new Point2D(random.nextDouble(width / 3f * 2, width), random.nextDouble(0, height - Bomber.SIZE))
                ),
                new Base().apply(base -> base.position = new Point2D(random.nextDouble(width / 3f * 2, width), random.nextDouble(0, height - Bomber.SIZE))
                )));
    }

    public void createTarget() {
        final var random = new Random();
        final var bases = entities.stream().filter(entity -> entity instanceof Base).toList();
        for (Entity base : bases) {
            entities.add(new Target().apply(target -> {
                target.origin = base.position + new Point2D(random.nextDouble(-Target.SIZE, Target.SIZE), random.nextDouble(-Target.SIZE, Target.SIZE));
            }));
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        pressedKeys.add(keyEvent.getCode());
    }


    public void onKeyReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());
    }

    public boolean isKeyPressed(KeyCode keyCode) {
        return pressedKeys.contains(keyCode);
    }
}
