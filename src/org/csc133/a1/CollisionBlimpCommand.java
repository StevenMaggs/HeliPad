package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollisionBlimpCommand extends Command {
    private GameWorld gameWorld;

    public CollisionBlimpCommand(GameWorld gameWorld) {
        super("collision_blimp");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.collisionWithRefuelingBlimp();
    }
}
