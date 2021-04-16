package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {
    private GameWorld gameWorld;

    public ExitCommand(GameWorld gameWorld) {
        super("exit");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.exit();
    }
}