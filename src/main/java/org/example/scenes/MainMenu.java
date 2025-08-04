package org.example.scenes;

import com.github.hanyaeger.api.scenes.StaticScene;
import com.github.hanyaeger.api.Coordinate2D;
import org.example.Arkanoid;
import org.example.entities.buttons.StartButton;

public class MainMenu extends StaticScene {

    private final Arkanoid arkanoid;

    public MainMenu(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;
    }

    @Override
    public void setupScene() {
//        setBackgroundImage("backgrounds/main-menu.png");
    }

    @Override
    public void setupEntities() {
        var startButton = new StartButton(new Coordinate2D(300, 400), arkanoid);
        addEntity(startButton);
    }
}
