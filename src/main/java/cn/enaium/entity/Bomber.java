package cn.enaium.entity;

import cn.enaium.Aircraft;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import manifold.ext.props.rt.api.var;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static cn.enaium.Aircraft.LOGGER;

/**
 * @author Enaium
 */
public class Bomber extends RemovableEntity {
    public static final int SIZE = 50;

    @var
    @Nullable
    Target target;

    /**
     * 是否到完成轰炸成功
     */
    @var
    boolean finish = false;

    /**
     * 是否返航成功
     */
    @var
    boolean back = false;

    public Bomber() {
        super("R", Color.RED, SIZE);
    }

    @Override
    public void update() {
        if (target == null) {
            // 搜寻目标
            Aircraft.inGame.entities.stream()
                    //过滤出Target
                    .filter(entity -> entity instanceof Target)
                    //转换为Target
                    .map(entity -> (Target) entity)
                    //过滤出活着的Target
                    .filter(entity -> entity.living && entity.visible && !entity.dead)
                    //过滤出没有被其他Airport锁定的Target
                    .filter(entity -> Aircraft.inGame.entities.stream().filter(it -> it instanceof Bomber).filter(it -> !it.dead).map(it -> ((Bomber) it).target).noneMatch(entity::equals))
                    //选择距离最近的Target
                    .min((o1, o2) -> (int) (o1.distance(this) - o2.distance(this))).ifPresent(it -> target = it);
        }

        // 干扰导弹
        if (target.isNotNull()) {
            Aircraft.inGame.entities.stream()
                    //过滤出Missile
                    .filter(entity -> entity instanceof Missile)
                    //转换为Missile
                    .map(entity -> (Missile) entity)
                    //过滤出活着的Missile
                    .filter(entity -> entity.living && entity.visible && !entity.dead)
                    //过滤出距离小于SIZE + 1-3的Missile
                    .filter(entity -> distance(entity) <= SIZE + new Random().nextInt(1, 3))
                    .forEach(entity -> {
                        // 10%概率干扰导弹
                        if (new Random().nextInt(0, 100) < 10) {
                            // 干扰成功
                            entity.kill();
                            LOGGER.log(Level.INFO, "Bomber:{} Missile:{} Disturb", id, entity.id);
                        }
                    });
        }

        @Nullable
        Point2D movePos = null;

        if (target != null) {
            movePos = target.position;
        } else if (finish) {
            movePos = origin;
        }

        if (movePos == null)
            return;

        move(movePos, new Random().nextInt(1, 3));

        if (finish) {
            if (position.distance(origin) <= SIZE) {
                back();
            }
        } else {
            if (isBounded(target)) {
                finish();
            }
        }
    }

    public void finish() {
        if (target != null) {
            target.kill();
            target = null;
            finish = true;
            back = false;
        }
    }

    public void back() {
        target = null;
        back = true;
        visible = false;
        living = false;
    }
}
