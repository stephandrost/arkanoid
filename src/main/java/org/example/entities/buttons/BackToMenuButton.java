package org.example.entities.buttons;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.example.Arkanoid;

/**
 * Button that navigates back to the main menu when clicked.
 */
public class BackToMenuButton extends Button {

    private final Arkanoid arkanoid;

    /**
     * Creates the back-to-menu button at the given location.
     *
     * @param location the position of the button center
     * @param arkanoid the main game instance used to trigger scene transitions
     */
    public BackToMenuButton(Coordinate2D location, Arkanoid arkanoid) {
        super(location, "Back to Menu", 24, Color.WHITE, Color.LIGHTGRAY);
        this.arkanoid = arkanoid;
    }

    /** Returns to the main menu when the button is clicked. */
    @Override
    public void onMouseButtonPressed(MouseButton button, Coordinate2D coordinate2D) {
        arkanoid.goToMainMenu();
    }
}
