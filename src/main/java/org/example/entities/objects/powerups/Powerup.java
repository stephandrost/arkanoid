package org.example.entities.objects.powerups;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;

import javafx.scene.paint.Color;
import org.example.entities.objects.Paddle;
import org.example.scenes.GameLevel;

import java.util.List;

/**
 * Base class for falling powerups.
 */
public abstract class Powerup extends DynamicRectangleEntity implements Collided, SceneBorderCrossingWatcher {

    public static final double WIDTH = 24;
    public static final double HEIGHT = 14;

    private static final double FALL_SPEED = 1.8;

    private final GameLevel gameLevel;

    protected Powerup(Coordinate2D initialPosition, GameLevel gameLevel, Color color) {
        super(initialPosition, new Size(WIDTH, HEIGHT));
        this.gameLevel = gameLevel;
        setFill(color);
        setMotion(FALL_SPEED, Direction.DOWN);
    }

    @Override
    public void onCollision(List<Collider> collidingObjects) {
        if (collidingObjects.stream().anyMatch(Paddle.class::isInstance)) {
            applyEffect();
            remove();
        }
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border == SceneBorder.BOTTOM) {
            remove();
        }
    }

    protected abstract void applyEffect();

    protected GameLevel getGameLevel() {
        return gameLevel;
    }
}
