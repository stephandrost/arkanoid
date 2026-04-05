package org.example.entities.objects;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Set;

/**
 * The player-controlled paddle at the bottom of the game field.
 * <p>
 * Moves left and right with the arrow keys and stops at the scene borders.
 * </p>
 */
public class Paddle extends DynamicRectangleEntity implements KeyListener, SceneBorderTouchingWatcher, Collider {

    private static final double SPEED = 7.0;
    /** Width of the paddle, also used by {@link Ball} to calculate bounce angles. */
    public static final double DEFAULT_WIDTH = 90;
    private static final double HEIGHT = 20;

    /**
     * Creates the paddle at the given position.
     *
     * @param initialPosition the starting position of the paddle
     */
    public Paddle(Coordinate2D initialPosition) {
        super(initialPosition, new Size(DEFAULT_WIDTH, HEIGHT));
        setFill(Color.DODGERBLUE);
    }

    /**
     * Resizes the paddle while preserving its center point as much as possible.
     * Clamps the result so the paddle always remains within horizontal scene bounds.
     *
     * @param width the new paddle width
     */
    public void setWidthKeepingCenter(double width) {
        double centerX = getBoundingBox().getMinX() + getWidth() / 2;
        setWidth(width);
        double newX = centerX - width / 2;
        double clampedX = Math.max(1, Math.min(newX, getSceneWidth() - getWidth() - 1));
        setAnchorLocationX(clampedX);
    }

    /** Resets the paddle to its default width. */
    public void resetWidth() {
        setWidthKeepingCenter(DEFAULT_WIDTH);
    }

    /**
     * Moves the paddle left or right based on the currently pressed keys.
     * Stops the paddle when no arrow key is held.
     *
     * @param pressedKeys the set of keys currently held down
     */
    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.LEFT)) {
            setMotion(SPEED, Direction.LEFT);
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setMotion(SPEED, Direction.RIGHT);
        } else {
            setSpeed(0);
        }
    }

    /**
     * Stops the paddle and clamps its position when it reaches the left or right border.
     *
     * @param border the border that was touched
     */
    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        switch (border) {
            case LEFT -> {
                setSpeed(0);
                setAnchorLocationX(1);
            }
            case RIGHT -> {
                setSpeed(0);
                setAnchorLocationX(getSceneWidth() - getWidth() - 1);
            }
            case TOP, BOTTOM -> {} // Paddle only reacts to horizontal borders
        }
    }
}
