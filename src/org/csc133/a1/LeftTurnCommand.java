package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftTurnCommand extends Command {
    private GameWorld gameWorld;

    public LeftTurnCommand(GameWorld gameWorld) {
        super("left_turn");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.changeStickAngleLeft();
    }
}