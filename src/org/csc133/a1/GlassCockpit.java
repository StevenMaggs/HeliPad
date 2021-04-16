package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;

import java.awt.*;

public class GlassCockpit extends Container {
    private GameClockComponent gameClockInstance;
    private ClockComponent fuelInstance;
    private ClockComponent damageInstance;
    private ClockComponent livesInstance;
    private ClockComponent lastInstance;
    private ClockComponent headingInstance;

    private GameWorld gameWorld = null;
    private Helicopter player = Helicopter.getInstance();

    public GlassCockpit(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        gameClockInstance = generateGameClockComponent(5, 5, ColorUtil.CYAN,
                ColorUtil.rgb(0, 155, 155), 2);
        fuelInstance = generateClockComponent(5, 5, ColorUtil.WHITE, 2, 4,
                null, null);
        damageInstance = generateClockComponent(5, 5, ColorUtil.GREEN, 2, 2,
                new int[] {50, 85}, new int[] {ColorUtil.YELLOW,
                        ColorUtil.rgb(255, 0, 0)});
        livesInstance = generateClockComponent(5, 5, ColorUtil.CYAN, 2, 1,
                null, null);
        lastInstance = generateClockComponent(5, 5, ColorUtil.CYAN, 2, 1,
                null, null);
        headingInstance = generateClockComponent(5, 5, ColorUtil.YELLOW, 2, 3,
                null, null);
    }

    public Form generateForm() {
        Form glassCockpitForm = new Form();
        glassCockpitForm.getToolbar().hideToolbar();
        glassCockpitForm.getAllStyles().setBgColor(ColorUtil.LTGRAY);

        Container glassCockPitContainer = new Container();
        glassCockPitContainer.getAllStyles().setBgTransparency(255);
        glassCockPitContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);

        Container gameClock = new Container();
        gameClock.getAllStyles().setBgTransparency(255);
        gameClock.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        gameClock.setLayout(new BorderLayout());
        gameClock.add(BorderLayout.NORTH, "GAME TIME");
        gameClock.add(BorderLayout.SOUTH, gameClockInstance);

        Container fuel = generateClockContainer("FUEL", fuelInstance);
        Container damage = generateClockContainer("DAMAGE", damageInstance);
        Container lives = generateClockContainer("LIVES", livesInstance);
        Container last = generateClockContainer("LAST", lastInstance);
        Container heading = generateClockContainer("HEADING", headingInstance);

        glassCockPitContainer.add(gameClock);
        glassCockPitContainer.add(fuel);
        glassCockPitContainer.add(damage);
        glassCockPitContainer.add(lives);
        glassCockPitContainer.add(last);
        glassCockPitContainer.add(heading);

        glassCockpitForm.add(glassCockPitContainer);
        return glassCockpitForm;
    }

    public void startTime() {
        gameClockInstance.startElapsedTime();
    }

    public void resetTime() {
        gameClockInstance.resetElapsedTime();
    }

    public void stopTime() {
        gameClockInstance.stopElapsedTime();
    }

    public int getElapsedTime() {
        return gameClockInstance.getElapsedTime();
    }

    public void update() {
        fuelInstance.setCurrentValue(player.getFuelLevel());
        damageInstance.setCurrentValue(player.getDamageLevel());
        livesInstance.setCurrentValue(gameWorld.getLives());
        lastInstance.setCurrentValue(player.getLastSkyScraperReached());
        headingInstance.setCurrentValue(player.getHeading());

        fuelInstance.repaint();
        damageInstance.repaint();
        livesInstance.repaint();
        lastInstance.repaint();
        headingInstance.repaint();
    }

    private Container generateClockContainer(String label,
                                             ClockComponent clockInstance) {
        Container clockContainer = new Container();
        clockContainer.getAllStyles().setBgTransparency(255);
        clockContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        clockContainer.setLayout(new BorderLayout());
        clockContainer.add(BorderLayout.NORTH, label);
        clockContainer.add(BorderLayout.SOUTH, clockInstance);

        return clockContainer;
    }

    private ClockComponent generateClockComponent(int m, int p,
                                                  int primaryLedColor,
                                                  int size,
                                                  int numDigitsShowing,
                                                  int[] colorSwapTriggers,
                                                  int[] colorSwapColors) {
        ClockComponent clockComponent = new ClockComponent(numDigitsShowing,
                colorSwapTriggers, colorSwapColors);
        clockComponent.getAllStyles().setPadding(p, p, p, p);
        clockComponent.getAllStyles().setMargin(m, m, m, m);
        clockComponent.setLedColor(primaryLedColor);
        clockComponent.setSizeScalingFactor(size);

        return clockComponent;
    }

    private GameClockComponent generateGameClockComponent(int m, int p,
                                                          int primaryLedColor,
                                                          int secondaryLedColor,
                                                          int size) {
        GameClockComponent gameClockComponent = new GameClockComponent();
        gameClockComponent.getAllStyles().setPadding(p, p, p, p);
        gameClockComponent.getAllStyles().setMargin(m, m, m, m);
        gameClockComponent.setLedColor(primaryLedColor, secondaryLedColor);
        gameClockComponent.setSizeScalingFactor(size);

        return gameClockComponent;
    }
}
