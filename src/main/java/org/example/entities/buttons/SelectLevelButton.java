package org.example.entities.buttons;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.example.Arkanoid;

/**
 * Button that navigates to the level selector when clicked.
 */
public class SelectLevelButton extends Button {

    private final Arkanoid arkanoid;

    /**
     * Creates the level selector button at the given location.
     *
     * @param location the position of the button center
     * @param arkanoid the main game instance used to trigger scene transitions
     */
    public SelectLevelButton(Coordinate2D location, Arkanoid arkanoid) {
        super(location, "Select Level", 32, Color.GREEN, Color.LIGHTGREEN);
        this.arkanoid = arkanoid;
    }

    /**
     * Navigates to the level selector when the button is clicked.
     */
    @Override
    public void onMouseButtonPressed(MouseButton button, Coordinate2D coordinate2D) {
        arkanoid.goToLevelSelect();
    }
}
