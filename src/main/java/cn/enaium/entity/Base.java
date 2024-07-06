package cn.enaium.entity;

import cn.enaium.Aircraft;
import javafx.scene.paint.Color;
import manifold.ext.props.rt.api.var;
import org.apache.logging.log4j.Level;

import static cn.enaium.Aircraft.LOGGER;

/**
 * @author Enaium
 */
public class Base extends NamedEntity {

    public static final int SIZE = 50;

    public int missileCount = 14;

    @var
    Missile last;

    public Base() {
        super("B", Color.BLUE, SIZE);
    }

    @Override
    public void update() {
        if (last == null) {
            Aircraft.inGame.entities.stream()
                    .filter(entity -> entity instanceof Bomber)
                    .map(entity -> (Bomber) entity)
                    .filter(entity -> entity.target.isNotNull() || entity.back)
                    .filter(entity -> entity.living && entity.visible && !entity.dead)
                    .min((o1, o2) -> (int) (o1.distance(this) - o2.distance(this)))
                    .ifPresent(it -> {
                        if (missileCount > 0) {
                            Aircraft.inGame.entities.add(last = new Missile(this.position.copy(), it.position.copy()).apply(missile -> {
                                missile.origin = position;
                                missileCount--;
                                LOGGER.log(Level.INFO, "Base:{} MissileCount:{}", id, missileCount);
                            }));
                        }
                    });
        } else {
            if (last.dead) {
                last = null;
            }
        }
    }
}
