package org.example.scenes;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import org.example.Arkanoid;
import org.example.entities.buttons.QuitButton;
import org.example.entities.buttons.StartButton;
import org.example.utils.FileManager;
import org.example.utils.FontManager;

/**
 * The main menu scene shown when the game starts.
 * <p>
 * Displays the title, top score, start/quit buttons, and plays background music.
 * </p>
 */
public class MainMenu extends StaticScene {

    private final Arkanoid arkanoid;
    private SoundClip backgroundMusic;
    private TextEntity topScoreText;

    /**
     * Creates the main menu and links it to the main game instance.
     *
     * @param arkanoid the main game instance used for scene transitions
     */
    public MainMenu(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;
    }

    /** Sets the background image and starts the menu music. */
    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/main-menu.png");
        startMusic();
    }

    /** Stops the background music if it is currently playing. */
    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    /** Restarts the background music and refreshes the displayed top score. */
    public void restartMusic() {
        stopMusic();
        startMusic();
        if (topScoreText != null) {
            topScoreText.setText("TOP SCORE: " + FileManager.read("topScore", "0"));
        }
    }

    private void startMusic() {
        backgroundMusic = new SoundClip("audio/main-menu.mp3");
        backgroundMusic.setVolume(0.5);
        backgroundMusic.play();
    }

    /** Adds all visual entities (title, top score, buttons, credits) to the scene. */
    @Override
    public void setupEntities() {
        var title = new TextEntity(new Coordinate2D(getWidth() / 2, 150), "ARKANOID");
        title.setFont(FontManager.getPressStartFont(64));
        title.setFill(Color.ORANGE);
        title.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(title);

        topScoreText = new TextEntity(new Coordinate2D(getWidth() / 2, 250), "TOP SCORE: " + FileManager.read("topScore", "0"));
        topScoreText.setFont(FontManager.getPressStartFont(24));
        topScoreText.setFill(Color.YELLOW);
        topScoreText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(topScoreText);

        var startButton = new StartButton(new Coordinate2D(getWidth() / 2, 380), arkanoid);
        addEntity(startButton);

        var quitButton = new QuitButton(new Coordinate2D(getWidth() / 2, 450));
        addEntity(quitButton);

        var credits = new TextEntity(new Coordinate2D(getWidth() / 2, 530), "© HAN 2025");
        credits.setFont(FontManager.getPressStartFont(16));
        credits.setFill(Color.LIGHTGRAY);
        credits.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(credits);

        var version = new TextEntity(new Coordinate2D(getWidth() - 10, getHeight() - 10), "v0.1");
        version.setFont(FontManager.getPressStartFont(12));
        version.setFill(Color.GRAY);
        version.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);
        addEntity(version);
    }
}
