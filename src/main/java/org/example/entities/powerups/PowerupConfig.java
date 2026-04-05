package org.example.entities.powerups;

/**
 * Central configuration for powerup mechanics.
 * Adjust these constants to tune gameplay balance without modifying implementation code.
 */
public final class PowerupConfig {
    /** Probability (0.0 to 1.0) that destroying a brick yields a powerup (3%). */
    public static final double POWERUP_TRIGGER_CHANCE = 0.03;

    /** Duration in milliseconds for the wide-paddle powerup effect (10 seconds). */
    public static final long WIDE_PADDLE_DURATION_MS = 10_000;

    /** Width of the paddle when the wide-paddle powerup is active. */
    public static final double WIDE_PADDLE_WIDTH = 145;

    /** Utility class; no instances should be created. */
    private PowerupConfig() {
    }
}
