package org.example.entities.objects.powerups;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import org.example.scenes.GameLevel;

/**
 * Powerup that temporarily enlarges the paddle.
 */
public class WiderPaddlePowerup extends Powerup {

    public static final Color COLOR = Color.DODGERBLUE;
    public static final double PADDLE_WIDTH = 145;
    public static final long DURATION_MS = 10_000;

    public WiderPaddlePowerup(Coordinate2D initialPosition, GameLevel gameLevel) {
        super(initialPosition, gameLevel, COLOR);
    }

    @Override
    protected void applyEffect() {
        getGameLevel().activateWidePaddle();
    }
}