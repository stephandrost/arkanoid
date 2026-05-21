package org.example.entities.objects.powerups;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import org.example.scenes.GameLevel;

/**
 * Powerup that grants one extra life.
 */
public class ExtraLifePowerup extends Powerup {

    public static final Color COLOR = Color.LIMEGREEN;

    public ExtraLifePowerup(Coordinate2D initialPosition, GameLevel gameLevel) {
        super(initialPosition, gameLevel, COLOR);
    }

    @Override
    protected void applyEffect() {
        getGameLevel().addLife();
    }
}