package org.example;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import org.example.scenes.GameLevel;
import org.example.scenes.MainMenu;

public class Arkanoid extends YaegerGame
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void setupGame() {
        setGameTitle("Arkanoid");
        setSize(new Size(800, 600));
    }

    @Override
    public void setupScenes() {
        addScene(0, new MainMenu(this));
        addScene(1, new GameLevel(this));
    }
}
