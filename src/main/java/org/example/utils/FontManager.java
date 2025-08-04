package org.example.utils;

import javafx.scene.text.Font;

public class FontManager {
    private static Font pressStartFont;

    public static Font getPressStartFont(double size) {
        if (pressStartFont == null) {
            pressStartFont = Font.loadFont(
                    FontManager.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
                    size
            );
        }
        return Font.font(pressStartFont.getName(), size); // Reuse font name with new size
    }
}
