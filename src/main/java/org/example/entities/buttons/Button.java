package org.example.entities.buttons;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.userinput.MouseEnterListener;
import com.github.hanyaeger.api.userinput.MouseExitListener;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import org.example.utils.FontManager;

/**
 * Abstract base class for all clickable text buttons in the game.
 * <p>
 * Handles shared styling (font, anchor, colors) and mouse hover effects via Yaeger's MouseEnterListener/MouseExitListener.
 * Subclasses only need to implement {@link #onMouseButtonPressed}.
 * </p>
 */
public abstract class Button extends TextEntity implements MouseButtonPressedListener, MouseEnterListener, MouseExitListener {

    private final Color defaultColor;
    private final Color hoverColor;

    /**
     * Creates a styled button at the given location.
     *
     * @param location     the position of the button center
     * @param text         the label shown on the button
     * @param fontSize     the font size to use
     * @param defaultColor the normal text color
     * @param hoverColor   the text color when the mouse hovers over the button
     */
    protected Button(Coordinate2D location, String text, double fontSize, Color defaultColor, Color hoverColor) {
        super(location, text);
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        setFont(FontManager.getPressStartFont(fontSize));
        setFill(defaultColor);
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
    }

    /**
     * Highlights the button and changes the cursor to a hand when hovered.
     */
    @Override
    public void onMouseEntered() {
        setFill(hoverColor);
        setCursor(Cursor.HAND);
    }

    /**
     * Restores the default color and cursor when the mouse leaves the button.
     */
    @Override
    public void onMouseExited() {
        setFill(defaultColor);
        setCursor(Cursor.DEFAULT);
    }
}
