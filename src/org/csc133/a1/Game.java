package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.UITimer;

import java.io.IOException;

public class Game extends Form implements Runnable {
    private GameWorld gameWorld;
    private GlassCockpit glassCockpit;
    private MapView mapView;
    private UITimer timer;
    private static int timeBetweenTicks = 20;

    private AccelerateCommand accelerateCommand;
    private BrakeCommand brakeCommand;
    private LeftTurnCommand leftTurnCommand;
    private RightTurnCommand rightTurnCommand;
    private CollisionNPHCommand collisionNPHCommand;
    private CollisionSkyScraperCommand collisionSkyScraperCommand;
    private CollisionBlimpCommand collisionBlimpCommand;
    private CollisionBirdCommand collisionBirdCommand;
    private ExitCommand exitCommand;
    private ChangeStrategiesCommand changeStrategiesCommand;
    private AboutInfoCommand aboutInfoCommand;
    private HelpInfoCommand helpInfoCommand;
    private PauseCommand pauseCommand;

    private Button accelerateButton;
    private Button brakeButton;
    private Button leftTurnButton;
    private Button rightTurnButton;

    public Game() {
        gameWorld = new GameWorld(this);
        gameWorld.init();

        mapView = gameWorld.getMapView();
        glassCockpit = gameWorld.getGlassCockpit();

        init();
        gameWorld.initializeSpawns();
    }

    private void init() {
        this.setTitle("HeliPad");
        this.setLayout(new BorderLayout());
        Style style = new Style();
        style.setBgColor(ColorUtil.rgb(176, 202, 102));
        this.getToolbar().setUnselectedStyle(style);

        generateCommands();
        generateSideMenu();
        addKeybindings();

        generateContainers();

        this.show();

        gameWorld.setGameWorldDimensions(mapView.getWidth(),
                mapView.getHeight());

        timer = new UITimer(this);
        timer.schedule(20, true, this);
    }

    private void generateContainers() {
        Container glassCockpitContainer = new Container();
        Form glassCockpitForm = glassCockpit.generateForm();
        glassCockpitContainer.add(glassCockpitForm);
        glassCockpitContainer.getAllStyles().setBgTransparency(255);
        glassCockpitContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);

        Container buttonContainer = new Container(new GridLayout(1, 4));
        buttonContainer.getAllStyles().setBgTransparency(255);
        buttonContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        generateButtons();
        buttonContainer.add(leftTurnButton);
        buttonContainer.add(brakeButton);
        buttonContainer.add(accelerateButton);
        buttonContainer.add(rightTurnButton);

        mapView.getAllStyles().setBgTransparency(255);

        this.add(BorderLayout.NORTH, glassCockpitContainer);
        this.add(BorderLayout.SOUTH, buttonContainer);
        this.add(BorderLayout.CENTER, mapView);
    }

    private void generateSideMenu() {
        Toolbar toolbar = this.getToolbar();

        toolbar.addMaterialCommandToSideMenu("Change Strategies",
                FontImage.MATERIAL_TRAFFIC, changeStrategiesCommand);
        toolbar.addMaterialCommandToSideMenu("About",
                FontImage.MATERIAL_INFO, aboutInfoCommand);
        toolbar.addMaterialCommandToSideMenu("Help",
                FontImage.MATERIAL_HELP, helpInfoCommand);
        toolbar.addMaterialCommandToSideMenu("Exit",
                FontImage.MATERIAL_EXIT_TO_APP, exitCommand);
    }

    private void addKeybindings() {
        this.addKeyListener('a', accelerateCommand);
        this.addKeyListener('b', brakeCommand);
        this.addKeyListener('l', leftTurnCommand);
        this.addKeyListener('r', rightTurnCommand);
        this.addKeyListener('n', collisionNPHCommand);
        this.addKeyListener('s', collisionSkyScraperCommand);
        this.addKeyListener('e', collisionBlimpCommand);
        this.addKeyListener('g', collisionBirdCommand);
        this.addKeyListener('x', exitCommand);
        this.addKeyListener('z', pauseCommand);
    }

    private void removeKeybindings() {
        this.removeKeyListener('a', accelerateCommand);
        this.removeKeyListener('b', brakeCommand);
        this.removeKeyListener('l', leftTurnCommand);
        this.removeKeyListener('r', rightTurnCommand);
        this.removeKeyListener('n', collisionNPHCommand);
        this.removeKeyListener('s', collisionSkyScraperCommand);
        this.removeKeyListener('e', collisionBlimpCommand);
        this.removeKeyListener('g', collisionBirdCommand);
        this.removeKeyListener('x', exitCommand);
    }

    private void generateCommands() {
        accelerateCommand = new AccelerateCommand(gameWorld);
        brakeCommand = new BrakeCommand(gameWorld);
        leftTurnCommand = new LeftTurnCommand(gameWorld);
        rightTurnCommand = new RightTurnCommand(gameWorld);
        collisionNPHCommand = new CollisionNPHCommand(gameWorld);
        collisionSkyScraperCommand = new CollisionSkyScraperCommand(gameWorld);
        collisionBlimpCommand = new CollisionBlimpCommand(gameWorld);
        collisionBirdCommand = new CollisionBirdCommand(gameWorld);
        exitCommand = new ExitCommand(gameWorld);
        changeStrategiesCommand = new ChangeStrategiesCommand(gameWorld);
        aboutInfoCommand = new AboutInfoCommand(gameWorld);
        helpInfoCommand = new HelpInfoCommand(gameWorld);
        pauseCommand = new PauseCommand(gameWorld);
    }

    private void generateButtons() {
        Image arrowRightImage = null;
        try {
            arrowRightImage = Image.createImage("/arrowRight.png");
            arrowRightImage = arrowRightImage.scaled(
                    arrowRightImage.getWidth() / 3,
                    arrowRightImage.getHeight() / 3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        accelerateButton = new Button(arrowRightImage.rotate90Degrees(true)
                        .flipVertically(true));
        accelerateButton.getDisabledStyle().setBgTransparency(50);
        accelerateButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
        accelerateButton.setCommand(accelerateCommand);
        accelerateButton.setText("");

        brakeButton = new Button(arrowRightImage.rotate90Degrees(true));
        brakeButton.getDisabledStyle().setBgTransparency(50);
        brakeButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
        brakeButton.setCommand(brakeCommand);
        brakeButton.setText("");

        leftTurnButton = new Button(arrowRightImage.flipHorizontally(true));
        leftTurnButton.getDisabledStyle().setBgTransparency(50);
        leftTurnButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
        leftTurnButton.setCommand(leftTurnCommand);
        leftTurnButton.setText("");

        rightTurnButton = new Button(arrowRightImage);
        rightTurnButton.getDisabledStyle().setBgTransparency(50);
        rightTurnButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
        rightTurnButton.setCommand(rightTurnCommand);
        rightTurnButton.setText("");
    }

    private void enableButtons() {
        accelerateButton.setEnabled(true);
        brakeButton.setEnabled(true);
        leftTurnButton.setEnabled(true);
        rightTurnButton.setEnabled(true);
    }

    private void disableButtons() {
        accelerateButton.setEnabled(false);
        brakeButton.setEnabled(false);
        leftTurnButton.setEnabled(false);
        rightTurnButton.setEnabled(false);
    }

    public void pause() {
        glassCockpit.stopTime();
        removeKeybindings();
        disableButtons();
        getToolbar().setEnabled(false);
        timer.cancel();
    }

    public void unpause() {
        glassCockpit.startTime();
        addKeybindings();
        enableButtons();
        getToolbar().setEnabled(true);
        timer.schedule(timeBetweenTicks, true, this);
    }

    public int getTimeBetweenTicks() {
        return timeBetweenTicks;
    }

    @Override
    public void run() {
        gameWorld.tick();
    }
}