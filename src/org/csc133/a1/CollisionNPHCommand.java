package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollisionNPHCommand extends Command {
    private GameWorld gameWorld;

    public CollisionNPHCommand(GameWorld gameWorld) {
        super("collision_nph");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.collisionWithHelicopter();
    }
}