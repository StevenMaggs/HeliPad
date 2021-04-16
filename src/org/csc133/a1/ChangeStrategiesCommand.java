package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ChangeStrategiesCommand extends Command {
    private GameWorld gameWorld;

    public ChangeStrategiesCommand(GameWorld gameWorld) {
        super("change_strategies");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.changeStrategies();
    }
}