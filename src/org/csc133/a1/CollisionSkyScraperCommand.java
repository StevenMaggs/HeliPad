package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollisionSkyScraperCommand extends Command {
    private GameWorld gameWorld;

    public CollisionSkyScraperCommand(GameWorld gameWorld) {
        super("collision_skyscraper");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.collisionWithSkyScraper();
    }
}
