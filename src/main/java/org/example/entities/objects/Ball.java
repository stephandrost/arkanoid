package org.example.entities.objects;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicCircleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import javafx.scene.paint.Color;
import org.example.scenes.GameLevel;

import java.util.List;

/**
 * The ball entity that bounces around the game field.
 * <p>
 * Handles wall bouncing, paddle deflection, brick destruction, and speed increases.
 * </p>
 */
public class Ball extends DynamicCircleEntity implements SceneBorderTouchingWatcher, Collided {

    private static final double RADIUS = 10;
    private static final double INITIAL_SPEED = 3.0;
    private static final double MAX_SPEED = 6.5;

    private double currentSpeed = INITIAL_SPEED;
    private final GameLevel gameLevel;

    /**
     * Creates the ball at the given position and links it to the game level.
     *
     * @param initialPosition the starting position of the ball
     * @param gameLevel       the game level used to report scoring and life loss
     */
    public Ball(Coordinate2D initialPosition, GameLevel gameLevel) {
        super(initialPosition);
        this.gameLevel = gameLevel;
        setRadius(RADIUS);
        setFill(Color.WHITE);
        setMotion(currentSpeed, Direction.UP_LEFT);
    }

    /**
     * Increases the ball speed by a fixed step, up to the maximum allowed speed.
     */
    public void increaseSpeed() {
        currentSpeed = Math.min(currentSpeed + 0.4, MAX_SPEED);
        setSpeed(currentSpeed);
    }

    /**
     * Reacts to the ball touching a scene border.
     * Left/right/top borders invert the relevant velocity component;
     * the bottom border causes the player to lose a life and resets the ball.
     *
     * @param border the border that was touched
     */
    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        switch (border) {
            case LEFT -> invertSpeedInDirection(Direction.LEFT);
            case RIGHT -> invertSpeedInDirection(Direction.RIGHT);
            case TOP -> invertSpeedInDirection(Direction.UP);
            case BOTTOM -> {
                gameLevel.loseLife();
                setAnchorLocation(new Coordinate2D(getSceneWidth() / 2, getSceneHeight() / 2));
                setMotion(currentSpeed, Direction.UP_LEFT);
            }
        }
    }

    /**
     * Handles collisions with the paddle and bricks.
        * Uses Yaeger vector helpers to reflect the ball off colliders.
     *
     * @param collidingObjects the list of objects the ball collided with this frame
     */
    @Override
    public void onCollision(List<Collider> collidingObjects) {
        for (Collider collider : collidingObjects) {
            if (collider instanceof Paddle paddle) {
                bounceOffPaddle(paddle);
            } else if (collider instanceof Brick brick) {
                bounceOffBrick(brick);
                if (brick.hit()) {
                    javafx.geometry.Bounds brickBounds = brick.getBoundingBox();
                    Coordinate2D brickPosition = new Coordinate2D(brickBounds.getMinX(), brickBounds.getMinY());
                    gameLevel.addScore(brickPosition);
                }
            }
        }
    }

    /**
        * Bounces the ball off a brick using vector reflection.
     *
     * @param brick the brick that was hit
     */
    private void bounceOffBrick(Brick brick) {
        if (brick instanceof YaegerEntity entity) {
            invertSpeedInDirection(angleTo(entity));
        }
    }

    /**
        * Bounces the ball off the paddle while enforcing enough upward motion.
     *
     * @param paddle the paddle that was hit
     */
    private void bounceOffPaddle(Paddle paddle) {
        if (getSpeedInDirection(Direction.DOWN) <= 0) {
            return;
        }

        if (paddle instanceof YaegerEntity entity) {
            invertSpeedInDirection(angleTo(entity));
            maximizeMotionInDirection(Direction.UP, currentSpeed * 0.55);
        }
    }
}
