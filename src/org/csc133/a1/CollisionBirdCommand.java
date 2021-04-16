package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollisionBirdCommand extends Command {
    private GameWorld gameWorld;

    public CollisionBirdCommand(GameWorld gameWorld) {
        super("collision_bird");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.collisionWithBird();
    }
}
