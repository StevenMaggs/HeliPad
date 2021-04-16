package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command {
    private GameWorld gameWorld;

    public BrakeCommand(GameWorld gameWorld) {
        super("brake");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.brake();
    }
}
