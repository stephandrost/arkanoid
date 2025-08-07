package org.example.entities.objects;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicCircleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import javafx.scene.paint.Color;

import java.util.List;

public class Ball extends DynamicCircleEntity implements SceneBorderTouchingWatcher, Collided {

    private static final double DIAMETER = 15;
    private static final double SPEED = 4;

    public Ball(Coordinate2D initialPosition) {
        super(initialPosition);
        setRadius(DIAMETER);
        setFill(Color.WHITE);
        setMotion(SPEED, Direction.UP_LEFT);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        switch (border) {
            case LEFT -> invertSpeedInDirection(Direction.LEFT);
            case RIGHT -> invertSpeedInDirection(Direction.RIGHT);
            case TOP -> invertSpeedInDirection(Direction.UP);
            case BOTTOM -> {
                invertSpeedInDirection(Direction.DOWN);
                // TODO: Remove the ball or reset its position?
                setAnchorLocation(new Coordinate2D(getSceneWidth() / 2, getSceneHeight() / 2));
            }
        }
    }

    @Override
    public void onCollision(List<Collider> collidingObjects) {
        for (Collider collider : collidingObjects) {
            if (collider instanceof Paddle) {
                setDirection(360 - getDirection());
            }
        }
    }
}
