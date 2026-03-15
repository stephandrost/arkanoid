package org.example.utils;

import javafx.scene.text.Font;

/**
 * Utility class for loading custom fonts used throughout the game.
 */
public class FontManager {
    private static Font pressStartFont;

    /**
     * Returns the "Press Start 2P" font at the requested size.
     * The font file is loaded once on first use and cached for subsequent calls.
     *
     * @param size the desired font size in points
     * @return the loaded {@link Font} at the given size
     */
    public static Font getPressStartFont(double size) {
        if (pressStartFont == null) {
            pressStartFont = Font.loadFont(
                    FontManager.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
                    size
            );
        }
        return Font.font(pressStartFont.getName(), size);
    }
}
