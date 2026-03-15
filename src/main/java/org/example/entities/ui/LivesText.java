package org.example.entities.ui;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;

/**
 * HUD label that displays the player's remaining lives in the top-right corner.
 */
public class LivesText extends GameText {

    /**
     * Creates the lives label at the given position.
     *
     * @param position the screen position of the label
     * @param lives    the initial number of lives to display
     */
    public LivesText(Coordinate2D position, int lives) {
        super(position, "LIVES: " + lives);
        setAnchorPoint(AnchorPoint.TOP_RIGHT);
    }

    /**
     * Updates the displayed lives count.
     *
     * @param lives the new number of lives to show
     */
    public void setLives(int lives) {
        setText("LIVES: " + lives);
    }
}
