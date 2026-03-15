package org.example.entities.ui;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import org.example.utils.FontManager;

/**
 * Abstract base class for HUD text elements displayed during gameplay.
 * <p>
 * Applies the shared font (size 16) and white fill used by all in-game labels.
 * </p>
 */
public abstract class GameText extends TextEntity {

    /**
     * Creates a styled HUD text entity.
     *
     * @param position the screen position of the text
     * @param text     the initial text to display
     */
    protected GameText(Coordinate2D position, String text) {
        super(position, text);
        setFont(FontManager.getPressStartFont(16));
        setFill(Color.WHITE);
    }
}
