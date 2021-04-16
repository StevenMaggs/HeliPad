package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command {
    private GameWorld gameWorld;

    public AccelerateCommand(GameWorld gameWorld) {
        super("accelerate");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.accelerate();
    }
}