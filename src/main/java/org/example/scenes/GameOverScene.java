package org.example.scenes;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import org.example.Arkanoid;
import org.example.entities.buttons.BackToMenuButton;
import org.example.utils.FileManager;
import org.example.utils.FontManager;

/**
 * The end screen displayed after the game finishes (win or game over).
 * <p>
 * Shows the result title, final score, all-time top score, and a back-to-menu button.
 * </p>
 */
public class GameOverScene extends StaticScene {

    private final Arkanoid arkanoid;
    private final boolean isWin;
    private final int score;

    /**
     * Creates the end screen.
     *
     * @param arkanoid the main game instance used for scene transitions
     * @param isWin    {@code true} if the player won, {@code false} for game over
     * @param score    the final score achieved
     */
    public GameOverScene(Arkanoid arkanoid, boolean isWin, int score) {
        this.arkanoid = arkanoid;
        this.isWin = isWin;
        this.score = score;
    }

    /** Sets the background image for the end screen. */
    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/main-menu.png");
    }

    /** Adds all end-screen entities (title, score, top score, back button) to the scene. */
    @Override
    public void setupEntities() {
        String titleLabel = isWin ? "YOU WIN!" : "GAME OVER";

        var titleText = new TextEntity(new Coordinate2D(getWidth() / 2, 160), titleLabel);
        titleText.setFont(FontManager.getPressStartFont(48));
        titleText.setFill(isWin ? Color.GOLD : Color.CRIMSON);
        titleText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(titleText);

        var scoreText = new TextEntity(new Coordinate2D(getWidth() / 2, 270), "SCORE: " + score);
        scoreText.setFont(FontManager.getPressStartFont(28));
        scoreText.setFill(Color.WHITE);
        scoreText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(scoreText);

        int topScore = Integer.parseInt(FileManager.read("topScore", "0"));
        var topScoreText = new TextEntity(new Coordinate2D(getWidth() / 2, 340), "TOP SCORE: " + topScore);
        topScoreText.setFont(FontManager.getPressStartFont(20));
        topScoreText.setFill(Color.YELLOW);
        topScoreText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(topScoreText);

        addEntity(new BackToMenuButton(new Coordinate2D(getWidth() / 2, 460), arkanoid));
    }
}
