package org.example.entities.buttons;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.Coordinate2D;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.example.utils.FontManager;

public class QuitButton extends TextEntity implements MouseButtonPressedListener {

    public QuitButton(Coordinate2D location) {
        super(location, "Quit Game");

        setFont(FontManager.getPressStartFont(32));
        setFill(Color.CRIMSON);
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
    }

    @Override
    public void onMouseButtonPressed(MouseButton button, Coordinate2D coordinate2D) {
        System.exit(0);
    }

    public void onMouseEntered() {
        setFill(Color.RED);
        setCursor(Cursor.HAND);
    }

    public void onMouseExited() {
        setFill(Color.CRIMSON);
        setCursor(Cursor.DEFAULT);
    }
}
