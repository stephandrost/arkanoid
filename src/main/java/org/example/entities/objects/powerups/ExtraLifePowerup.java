package org.example.entities.objects.powerups;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import org.example.scenes.GameLevel;

/**
 * Powerup that grants one extra life.
 */
public class ExtraLifePowerup extends Powerup {

    public static final Color COLOR = Color.LIMEGREEN;

    /**
     * Creates an extra-life powerup.
     *
     * @param initialPosition the spawn position
     * @param gameLevel the active game level
     */
    public ExtraLifePowerup(Coordinate2D initialPosition, GameLevel gameLevel) {
        super(initialPosition, gameLevel, COLOR);
    }

    /** Grants the player one extra life. */
    @Override
    protected void applyEffect() {
        getGameLevel().addLife();
    }
}