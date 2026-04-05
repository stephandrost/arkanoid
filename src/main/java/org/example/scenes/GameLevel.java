package org.example.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.DynamicScene;
import javafx.scene.paint.Color;
import org.example.Arkanoid;
import org.example.entities.powerups.PowerupConfig;
import org.example.entities.powerups.PowerupIndicator;
import org.example.entities.powerups.PowerupType;
import org.example.entities.objects.Ball;
import org.example.entities.objects.Brick;
import org.example.entities.objects.Paddle;
import org.example.entities.ui.LivesText;
import org.example.entities.ui.ScoreText;
import org.example.utils.FileManager;

import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * The active gameplay scene containing the paddle, ball, bricks, and HUD.
 * <p>
 * Tracks score and lives, speeds up the ball every {@value #SPEED_UP_EVERY} bricks,
 * and transitions to the end screen on win or game over.
 * </p>
 */
public class GameLevel extends DynamicScene {

    private static final int INITIAL_LIVES = 4;
    private static final int SCORE_PER_BRICK = 10;
    private static final int ROWS = 7;
    private static final int COLS = 8;
    private static final int TOTAL_BRICKS = ROWS * COLS;
    private static final int SPEED_UP_EVERY = 6;
    /** Rows with index below this value require two hits to destroy. */
    private static final int DOUBLE_HIT_ROWS = 1;

    private final Arkanoid arkanoid;
    private int score = 0;
    private int lives = INITIAL_LIVES;
    private int bricksDestroyed = 0;
    private Ball ball;
    private Paddle paddle;
    private ScoreText scoreText;
    private LivesText livesText;
    private SoundClip backgroundMusic;
    private long widePaddleUntilMs = 0;
    private final Map<PowerupIndicator, Long> indicatorLifetimes = new HashMap<>();

    /**
     * Creates the game level and links it to the main game instance.
     *
     * @param arkanoid the main game instance used for scene transitions
     */
    public GameLevel(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;
    }

    /** Sets the background image and starts the gameplay music. */
    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/game-level.png");
        startMusic();
    }

    /** Starts the background music for the game level. */
    private void startMusic() {
        backgroundMusic = new SoundClip("audio/main-menu.mp3");
        backgroundMusic.setVolume(0.5);
        backgroundMusic.play();
    }

    /** Adds all game entities (HUD, paddle, ball, bricks) to the scene. */
    @Override
    public void setupEntities() {
        scoreText = new ScoreText(new Coordinate2D(10, 10));
        addEntity(scoreText);

        livesText = new LivesText(new Coordinate2D(getWidth() - 10, 10), INITIAL_LIVES);
        addEntity(livesText);

        paddle = new Paddle(new Coordinate2D(getWidth() / 2 - Paddle.DEFAULT_WIDTH / 2, getHeight() - 50));
        addEntity(paddle);

        ball = new Ball(new Coordinate2D(getWidth() / 2, getHeight() / 2), this);
        addEntity(ball);

        addBricks();
        getTimers().add(new Timer(100) {
            @Override
            public void onAnimationUpdate(long timestamp) {
                updateTimedPowerups();
            }
        });
    }

    private void addBricks() {
        Color[] colors = {Color.RED, Color.CRIMSON, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.DEEPSKYBLUE};
        double gap = 10;
        double totalWidth = COLS * Brick.WIDTH + (COLS - 1) * gap;
        double startX = (getWidth() - totalWidth) / 2;

        for (int row = 0; row < ROWS; row++) {
            int hits = (row < DOUBLE_HIT_ROWS) ? 2 : 1;
            for (int col = 0; col < COLS; col++) {
                double x = startX + col * (Brick.WIDTH + gap);
                double y = 80 + row * (Brick.HEIGHT + gap);
                addEntity(new Brick(new Coordinate2D(x, y), colors[row], hits));
            }
        }
    }

    /**
     * Called by {@link Ball} when a brick is destroyed.
     * Increases the score, optionally speeds up the ball, triggers powerup spawning, and checks for a win.
     *
     * @param destroyedBrickPosition the location where the brick was destroyed (used for powerup spawning)
     */
    public void addScore(Coordinate2D destroyedBrickPosition) {
        score += SCORE_PER_BRICK;
        bricksDestroyed++;
        scoreText.setScore(score);

        maybeSpawnPowerup(destroyedBrickPosition);

        if (bricksDestroyed % SPEED_UP_EVERY == 0) {
            ball.increaseSpeed();
        }

        if (bricksDestroyed >= TOTAL_BRICKS) {
            saveTopScore();
            arkanoid.showEndScreen(score, true);
        }
    }

    private void maybeSpawnPowerup(Coordinate2D brickPosition) {
        if (ThreadLocalRandom.current().nextDouble() > PowerupConfig.POWERUP_TRIGGER_CHANCE) {
            return;
        }

        PowerupType[] types = PowerupType.values();
        PowerupType type = types[ThreadLocalRandom.current().nextInt(types.length)];

        type.apply(this);

        double indicatorX = brickPosition.getX() + Brick.WIDTH / 2;
        double indicatorY = brickPosition.getY() + Brick.HEIGHT / 2;
        PowerupIndicator indicator = new PowerupIndicator(new Coordinate2D(indicatorX, indicatorY), type);
        addEntity(indicator);
        indicatorLifetimes.put(indicator, System.currentTimeMillis());
    }

    /**
     * Called by {@link Ball} when it falls below the bottom border.
     * Decrements lives and transitions to the game-over screen if none remain.
     */
    public void loseLife() {
        lives--;
        livesText.setLives(lives);
        if (lives <= 0) {
            saveTopScore();
            arkanoid.showEndScreen(score, false);
        }
    }

    /**
     * Adds one life and updates the HUD.
     */
    public void addLife() {
        lives++;
        livesText.setLives(lives);
    }

    /**
     * Activates (or extends) the wide-paddle powerup timer.
     * If already active, the duration is extended by the powerup duration.
     */
    public void activateWidePaddle() {
        paddle.setWidthKeepingCenter(PowerupConfig.WIDE_PADDLE_WIDTH);
        long now = System.currentTimeMillis();
        long baseTime = Math.max(now, widePaddleUntilMs);
        widePaddleUntilMs = baseTime + PowerupConfig.WIDE_PADDLE_DURATION_MS;
    }

    /**
     * Updates timed powerup effects and removes expired indicators.
     * Called periodically by the scene timer.
     */
    private void updateTimedPowerups() {
        // Update wide-paddle timer
        if (widePaddleUntilMs == 0) {
            // No-op if wide paddle is not active
        } else if (System.currentTimeMillis() >= widePaddleUntilMs) {
            widePaddleUntilMs = 0;
            paddle.resetWidth();
        }

        // Remove expired indicators (1.2 second display duration)
        final long INDICATOR_DURATION_MS = 1_200;
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<PowerupIndicator, Long>> iter = indicatorLifetimes.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<PowerupIndicator, Long> entry = iter.next();
            if (now - entry.getValue() >= INDICATOR_DURATION_MS) {
                entry.getKey().remove();
                iter.remove();
            }
        }
    }

    private void saveTopScore() {
        int topScore = Integer.parseInt(FileManager.read("topScore", "0"));
        if (score > topScore) {
            FileManager.write("topScore", String.valueOf(score));
        }
    }
}
