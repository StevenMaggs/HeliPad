package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightTurnCommand extends Command {
    private GameWorld gameWorld;

    public RightTurnCommand(GameWorld gameWorld) {
        super("right_turn");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.changeStickAngleRight();
    }
}