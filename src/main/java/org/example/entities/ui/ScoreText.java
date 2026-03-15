package org.example.entities.ui;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;

/**
 * HUD label that displays the player's current score in the top-left corner.
 */
public class ScoreText extends GameText {

    /**
     * Creates the score label at the given position, starting at zero.
     *
     * @param position the screen position of the label
     */
    public ScoreText(Coordinate2D position) {
        super(position, "SCORE: 0");
        setAnchorPoint(AnchorPoint.TOP_LEFT);
    }

    /**
     * Updates the displayed score.
     *
     * @param score the new score value to show
     */
    public void setScore(int score) {
        setText("SCORE: " + score);
    }
}
