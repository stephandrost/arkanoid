package org.example.entities.powerups;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import org.example.utils.FontManager;

/**
 * A brief visual indicator displayed when a powerup is triggered.
 * Shows a symbol (★ or ♥) at the brick location for approximately 1.2 seconds.
 */
public class PowerupIndicator extends TextEntity {

    /**
     * Creates a powerup indicator at the given position.
     *
     * @param position the location where the indicator should appear
     * @param type the powerup type (determines the symbol and color)
     */
    public PowerupIndicator(Coordinate2D position, PowerupType type) {
        super(position, getLabel(type));
        setFill(type.getColor());
        setFont(FontManager.getPressStartFont(12));
    }

    private static String getLabel(PowerupType type) {
        return switch (type) {
            case WIDER_PADDLE -> "★";
            case EXTRA_LIFE -> "♥";
        };
    }
}
