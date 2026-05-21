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
 * Abstract base class for all falling powerups.
 * Handles shared movement, collision logic, and cleanup behavior.
 */
public abstract class Powerup extends DynamicRectangleEntity implements Collided, SceneBorderCrossingWatcher {

    public static final double WIDTH = 24;
    public static final double HEIGHT = 14;

    private static final double FALL_SPEED = 1.8;

    private final GameLevel gameLevel;

    /**
     * Creates a new powerup at the given position.
     *
     * @param initialPosition the spawn position of the powerup
     * @param gameLevel the active game level
     * @param color the display color of the powerup
     */
    protected Powerup(Coordinate2D initialPosition, GameLevel gameLevel, Color color) {
        super(initialPosition, new Size(WIDTH, HEIGHT));
        this.gameLevel = gameLevel;
        setFill(color);
        setMotion(FALL_SPEED, Direction.DOWN);
    }

    /**
     * Applies the powerup effect when colliding with the paddle.
     *
     * @param collidingObjects the entities currently colliding with this powerup
     */
    @Override
    public void onCollision(List<Collider> collidingObjects) {
        if (collidingObjects.stream().anyMatch(Paddle.class::isInstance)) {
            applyEffect();
            remove();
        }
    }

     /**
     * Removes the powerup when it leaves the screen at the bottom border.
     *
     * @param border the crossed scene border
     */
    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border == SceneBorder.BOTTOM) {
            remove();
        }
    }

     /**
     * Applies this powerup's gameplay effect.
     * Implemented polymorphically by subclasses.
     */
    protected abstract void applyEffect();

    /**
     * Returns the active game level.
     *
     * @return the current game level
     */
    protected GameLevel getGameLevel() {
        return gameLevel;
    }
}
