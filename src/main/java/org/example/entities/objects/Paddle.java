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

public class Paddle extends DynamicRectangleEntity implements KeyListener, SceneBorderTouchingWatcher, Collider {

    private static final double SPEED = 5.0;
    private static final double WIDTH = 100;
    private static final double HEIGHT = 20;

    public Paddle(Coordinate2D initialPosition) {
        super(initialPosition, new Size(WIDTH, HEIGHT));
        setFill(Color.DODGERBLUE);
    }

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
        }
    }
}
