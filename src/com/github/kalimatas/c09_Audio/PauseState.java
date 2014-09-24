package com.github.kalimatas.c09_Audio;

import com.github.kalimatas.c09_Audio.GUI.Button;
import com.github.kalimatas.c09_Audio.GUI.Callback;
import com.github.kalimatas.c09_Audio.GUI.Container;
import org.jsfml.graphics.*;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

public class PauseState extends State {
    private Text pausedText = new Text();
    private Container GUIContainer = new Container();

    public PauseState(StateStack stack, Context context) {
        super(stack, context);

        Font font = context.fonts.getFont(Fonts.MAIN);
        Vector2f viewSize = context.window.getView().getSize();

        pausedText.setFont(font);
        pausedText.setString("Game Paused");
        pausedText.setCharacterSize(70);
        Utility.centerOrigin(pausedText);
        pausedText.setPosition(0.5f * viewSize.x, 0.4f * viewSize.y);

        Button returnButton = new Button(context.fonts, context.textures);
        returnButton.setPosition(0.5f * viewSize.x -100, 0.4f * viewSize.y + 75);
        returnButton.setText("Return");
        returnButton.setCallback(new Callback() {
            @Override
            public void invoke() {
                requestStackPop();

                // Resume mission music. As there is no destructor method in Java,
                // we put this code here.
                getContext().music.setPaused(false);
            }
        });

        Button backToMenuButton = new Button(context.fonts, context.textures);
        backToMenuButton.setPosition(0.5f * viewSize.x - 100, 0.4f * viewSize.y + 125);
        backToMenuButton.setText("Back to menu");
        backToMenuButton.setCallback(new Callback() {
            @Override
            public void invoke() {
                requestStateClear();
                requestStackPush(States.MENU);
            }
        });

        GUIContainer.pack(returnButton);
        GUIContainer.pack(backToMenuButton);

        getContext().music.setPaused(true);
    }

    @Override
    public void draw() {
        RenderWindow window = getContext().window;
        window.setView(window.getDefaultView());

        RectangleShape backgroundShape = new RectangleShape();
        backgroundShape.setFillColor(new Color(0, 0, 0, 150));
        backgroundShape.setSize(window.getView().getSize());

        window.draw(backgroundShape);
        window.draw(pausedText);
        window.draw(GUIContainer);
    }

    @Override
    public boolean update(Time dt) {
        return false;
    }

    @Override
    public boolean handleEvent(Event event) {
        GUIContainer.handleEvent(event);
        return false;
    }
}
