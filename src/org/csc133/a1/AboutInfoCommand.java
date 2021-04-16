package org.csc133.a1;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AboutInfoCommand extends Command {
    private GameWorld gameWorld;

    public AboutInfoCommand(GameWorld gameWorld) {
        super("about_info");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.aboutInfo();
    }
}