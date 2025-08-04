package org.example.entities.buttons;

import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.YaegerGame;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StartButton extends TextEntity implements MouseButtonPressedListener {

    private final YaegerGame game;

    public StartButton(Coordinate2D location, YaegerGame game) {
        super(location, "Start Game");
        this.game = game;
        setFont(Font.font("Arial", FontWeight.BOLD, 30));
        setFill(Color.DODGERBLUE);
    }

    @Override
    public void onMouseButtonPressed(MouseButton button, Coordinate2D coordinate2D) {
        game.setActiveScene(1); // Switch to GameLevel
    }
}
