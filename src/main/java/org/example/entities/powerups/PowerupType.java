package org.example.entities.powerups;

import javafx.scene.paint.Color;
import org.example.scenes.GameLevel;

/**
 * Defines available powerup effects and their visual metadata.
 * Each powerup type encapsulates its effect logic, making it easy to add new powerups
 * by simply adding another enum constant with an apply implementation.
 */
public enum PowerupType {
    /**
     * Temporarily enlarges the paddle for easier ball catching.
     * Applies to the game level and displays a star (★) indicator.
     */
    WIDER_PADDLE(Color.DODGERBLUE) {
        @Override
        public void apply(GameLevel gameLevel) {
            gameLevel.activateWidePaddle();
        }
    },

    /**
     * Grants an additional life to the player.
     * Displays a heart (♥) indicator.
     */
    EXTRA_LIFE(Color.LIMEGREEN) {
        @Override
        public void apply(GameLevel gameLevel) {
            gameLevel.addLife();
        }
    };

    private final Color color;

    PowerupType(Color color) {
        this.color = color;
    }

    /**
     * Returns the visual color associated with this powerup type.
     *
     * @return the color used for displaying the indicator
     */
    public Color getColor() {
        return color;
    }

    /**
     * Applies this powerup's effect to the given game level.
     *
     * @param gameLevel the game level to apply the effect to
     */
    public abstract void apply(GameLevel gameLevel);
}
