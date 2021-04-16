package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command {
    private GameWorld gameWorld;

    public PauseCommand(GameWorld gameWorld) {
        super("pause");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.pause();
    }
}
