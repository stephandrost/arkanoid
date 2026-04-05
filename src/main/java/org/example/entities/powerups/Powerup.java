package org.example.entities.powerups;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import org.example.entities.objects.Paddle;
import org.example.scenes.GameLevel;

import java.util.List;

/**
 * A falling collectible that applies a gameplay effect when caught by the paddle.
 */
public class Powerup extends DynamicRectangleEntity implements Collided, SceneBorderCrossingWatcher {

    public static final double WIDTH = 24;
    public static final double HEIGHT = 14;

    private static final double FALL_SPEED = 1.8;

    private final PowerupType type;
    private final GameLevel gameLevel;

    public Powerup(Coordinate2D initialPosition, PowerupType type, GameLevel gameLevel) {
        super(initialPosition, new Size(WIDTH, HEIGHT));
        this.type = type;
        this.gameLevel = gameLevel;
        setFill(type.getColor());
        setMotion(FALL_SPEED, Direction.DOWN);
    }

    public PowerupType getType() {
        return type;
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        if (border == SceneBorder.BOTTOM) {
            remove();
        }
    }

    @Override
    public void onCollision(List<Collider> collidingObjects) {
        for (Collider collider : collidingObjects) {
            if (collider instanceof Paddle) {
                type.apply(gameLevel);
                remove();
                return;
            }
        }
    }
}
