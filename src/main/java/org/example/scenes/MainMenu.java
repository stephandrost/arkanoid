package org.example.scenes;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.StaticScene;
import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import org.example.Arkanoid;
import org.example.entities.buttons.QuitButton;
import org.example.entities.buttons.StartButton;
import org.example.utils.FileManager;
import org.example.utils.FontManager;

public class MainMenu extends StaticScene {

    private final Arkanoid arkanoid;
    private SoundClip backgroundMusic;

    public MainMenu(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/main-menu.png");
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

        var startButton = new StartButton(new Coordinate2D(getWidth() / 2, 400), arkanoid);
        addEntity(startButton);

        var quitButton = new QuitButton(new Coordinate2D(getWidth() / 2, 460));
        addEntity(quitButton);

        var topScore = new TextEntity(new Coordinate2D(getWidth() / 2, 250), "TOP SCORE: " + FileManager.read("topScore", "0"));
        topScore.setFont(FontManager.getPressStartFont(24));
        topScore.setFill(Color.YELLOW);
        topScore.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(topScore);

        var credits = new TextEntity(new Coordinate2D(getWidth() / 2, 500), "© HAN 2025");
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
