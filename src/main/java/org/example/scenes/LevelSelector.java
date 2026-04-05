package org.example.scenes;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import org.example.Arkanoid;
import org.example.utils.FontManager;

/**
 * The level selector scene for choosing game difficulty or level.
 * <p>
 * Currently a placeholder for future level selection features.
 * The {@code arkanoid} field is stored for future scene navigation when levels are implemented.
 * </p>
 */
public class LevelSelector extends StaticScene {
    @SuppressWarnings("unused")
    private final Arkanoid arkanoid;
    private SoundClip backgroundMusic;

    /**
     * Creates the level selector scene.
     *
     * @param arkanoid the main game instance used for scene transitions
     */
    public LevelSelector(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;
    }

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
    }

    private void startMusic() {
        backgroundMusic = new SoundClip("audio/main-menu.mp3");
        backgroundMusic.setVolume(0.5);
        backgroundMusic.play();
    }

    @Override
    public void setupEntities() {
        var title = new TextEntity(new Coordinate2D(getWidth() / 2, 150), "ARKANOID");
        title.setFont(FontManager.getPressStartFont(64));
        title.setFill(Color.ORANGE);
        title.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(title);

    }

}
