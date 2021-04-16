package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class HelpInfoCommand extends Command {
    private GameWorld gameWorld;

    public HelpInfoCommand(GameWorld gameWorld) {
        super("help_info");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.helpInfo();
    }
}