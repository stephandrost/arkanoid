package org.example.entities.objects;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Direction;
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
     * Deflects off the paddle based on hit position; bounces off bricks based on which side was hit.
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
                javafx.geometry.Bounds brickBounds = brick.getBoundingBox();
                Coordinate2D brickPosition = new Coordinate2D(brickBounds.getMinX(), brickBounds.getMinY());
                if (brick.hit()) {
                    gameLevel.addScore(brickPosition);
                }
            }
        }
    }

    /**
     * Bounces the ball off a brick based on which side was hit.
     * Uses AABB collision detection with bounding boxes to determine the bounce direction.
     *
     * @param brick the brick that was hit
     */
    private void bounceOffBrick(Brick brick) {
        javafx.geometry.Bounds ballBounds = getBoundingBox();
        javafx.geometry.Bounds brickBounds = brick.getBoundingBox();

        double overlapLeft = ballBounds.getCenterX() + RADIUS - brickBounds.getMinX();
        double overlapRight = brickBounds.getMaxX() - (ballBounds.getCenterX() - RADIUS);
        double overlapTop = ballBounds.getCenterY() + RADIUS - brickBounds.getMinY();
        double overlapBottom = brickBounds.getMaxY() - (ballBounds.getCenterY() - RADIUS);

        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapLeft || minOverlap == overlapRight) {
            invertSpeedInDirection(Direction.LEFT);
        } else {
            invertSpeedInDirection(Direction.UP);
        }
    }

    /**
     * Calculates the bounce angle based on where the ball hits the paddle.
     * Hitting the left edge deflects sharply left; the right edge deflects sharply right.
     *
     * @param paddle the paddle that was hit
     */
    private void bounceOffPaddle(Paddle paddle) {
        javafx.geometry.Bounds ballBounds = getBoundingBox();
        javafx.geometry.Bounds paddleBounds = paddle.getBoundingBox();

        double ballY = ballBounds.getCenterY();
        double paddleY = paddleBounds.getMinY();
        double ballX = ballBounds.getCenterX();
        double paddleLeft = paddleBounds.getMinX();
        double paddleRight = paddleBounds.getMaxX();

        if (ballY > paddleY + 30) {
            return;
        }

        if (ballX < paddleLeft - RADIUS * 1.5 || ballX > paddleRight + RADIUS * 1.5) {
            return;
        }

        double paddleCenterX = paddleLeft + (paddleRight - paddleLeft) / 2;
        double hit = (ballX - paddleCenterX) / ((paddleRight - paddleLeft) / 2);
        hit = Math.max(-1.0, Math.min(1.0, hit));
        double angle = 270 + hit * 60;
        setMotion(currentSpeed, angle);
    }
}
