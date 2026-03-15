package org.example.entities.objects;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import javafx.scene.paint.Color;

/**
 * A single destructible brick that the ball can break.
 * Bricks can require more than one hit; after the first hit they turn visually darker.
 */
public class Brick extends DynamicRectangleEntity implements Collider {

    /** Width of a single brick tile. */
    public static final double WIDTH = 80;
    /** Height of a single brick tile. */
    public static final double HEIGHT = 25;

    private int hitsLeft;
    private final Color damagedColor;

    /**
     * Creates a brick at the given position with the specified color and hit count.
     *
     * @param position  the top-left position of the brick
     * @param color     the fill color when at full health
     * @param hitsLeft  the number of hits required to destroy this brick
     */
    public Brick(Coordinate2D position, Color color, int hitsLeft) {
        super(position, new Size(WIDTH, HEIGHT));
        this.hitsLeft = hitsLeft;
        this.damagedColor = color.deriveColor(0, 0.5, 0.6, 1.0);
        setFill(color);
    }

    /**
     * Registers a hit on this brick.
     * If the last hit was taken the brick removes itself.
     *
     * @return {@code true} if the brick was destroyed, {@code false} if it still has hits remaining
     */
    public boolean hit() {
        hitsLeft--;
        if (hitsLeft <= 0) {
            remove();
            return true;
        }
        setFill(damagedColor);
        return false;
    }
}
