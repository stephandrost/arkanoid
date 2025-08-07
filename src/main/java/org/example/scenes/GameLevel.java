package org.example.scenes;

import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.Coordinate2D;
import org.example.Arkanoid;
import org.example.entities.objects.Ball;
import org.example.entities.objects.Paddle;

public class GameLevel extends DynamicScene {

    private final Arkanoid arkanoid;
    private SoundClip backgroundMusic;

    public GameLevel(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/game-level.png");
    }

    @Override
    public void setupEntities() {
        addEntity(new Paddle(new Coordinate2D(getWidth() / 2 - 50, getHeight() - 50)));
        addEntity(new Ball(new Coordinate2D(getWidth() / 2, getHeight() / 2)));
        // blocks
        // score/health UI
    }
}
