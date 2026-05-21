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

    /**
     * Creates a wide-paddle powerup.
     *
     * @param initialPosition the spawn position
     * @param gameLevel the active game level
     */
    public WiderPaddlePowerup(Coordinate2D initialPosition, GameLevel gameLevel) {
        super(initialPosition, gameLevel, COLOR);
    }

    /** Activates the wide-paddle effect. */
    @Override
    protected void applyEffect() {
        getGameLevel().activateWidePaddle();
    }
}