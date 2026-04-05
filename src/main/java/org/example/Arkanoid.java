package org.example;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import org.example.scenes.GameLevel;
import org.example.scenes.GameOverScene;
import org.example.scenes.LevelSelector;
import org.example.scenes.MainMenu;

/**
 * Main entry point for the Arkanoid game.
 * <p>
 * Manages scene transitions between the main menu, game level, and end screen.
 * </p>
 */
public class Arkanoid extends YaegerGame {

    private MainMenu mainMenu;

    /**
     * Launches the game application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Configures the game title and window size.
     */
    @Override
    public void setupGame() {
        setGameTitle("Arkanoid");
        setSize(new Size(800, 600));
    }

    /**
     * Registers all scenes with the game engine.
     */
    @Override
    public void setupScenes() {
        mainMenu = new MainMenu(this);
        addScene(0, mainMenu);
        addScene(1, new GameLevel(this));
        addScene(3, new LevelSelector(this));
    }

    /**
     * Stops the menu music, creates a fresh game level and activates it.
     */
    public void startGame() {
        mainMenu.stopMusic();
        addScene(1, new GameLevel(this));
        setActiveScene(1);
    }

    /**
     * Shows the end screen (win or game-over) with the player's final score.
     *
     * @param score the score achieved during the game
     * @param isWin {@code true} if the player won, {@code false} for game over
     */
    public void showEndScreen(int score, boolean isWin) {
        addScene(2, new GameOverScene(this, isWin, score));
        setActiveScene(2);
    }

    /**
     * Restarts the menu music and returns to the main menu scene.
     */
    public void goToMainMenu() {
        mainMenu.restartMusic();
        setActiveScene(0);
    }

    /**
     * Navigates to the level selector scene.
     */
    public void goToLevelSelect() {
        setActiveScene(3);
    }
}
