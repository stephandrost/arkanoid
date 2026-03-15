package org.example.entities.buttons;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

/**
 * Button that exits the application when clicked.
 */
public class QuitButton extends Button {

    /**
     * Creates the quit button at the given location.
     *
     * @param location the position of the button center
     */
    public QuitButton(Coordinate2D location) {
        super(location, "Quit Game", 32, Color.CRIMSON, Color.RED);
    }

    /** Exits the application when the button is clicked. */
    @Override
    public void onMouseButtonPressed(MouseButton button, Coordinate2D coordinate2D) {
        System.exit(0);
    }
}
