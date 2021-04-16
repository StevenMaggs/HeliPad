package org.csc133.a1;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import org.w3c.dom.Text;

import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    // the master container for every single GameObject in the GameWorld
    private ArrayList<GameObject> gameObjectCollection = null;
    private int gameWorldWidth;
    private int gameWorldHeight;
    private int helicopterCollisionDamage = 20;
    private int helicopterCollisionColorChange = 50;
    private ArrayList<SkyScraper> skyScrapers;
    private int lastSkyScraper;
    private int lives = 3;
    private int timeBetweenTicks;
    private Game game;
    private Helicopter player = Helicopter.getInstance();
    private GlassCockpit glassCockpit = null;
    private MapView mapView = null;
    private boolean isPaused = false;

    public GameWorld(Game game) {
        this.game = game;
    }

    public void init() {
        timeBetweenTicks = game.getTimeBetweenTicks();
        gameObjectCollection = new ArrayList<GameObject>();
        skyScrapers = new ArrayList<SkyScraper>();

        mapView = new MapView(gameObjectCollection);
        glassCockpit = new GlassCockpit(this);
    }

    public void initializeSpawns() {
        // spawn all skyscrapers in ascending sequenceNumber order
        SkyScraper initialSkyScraper =
                spawnSkyScraper(0, new Point(560, 250));
        spawnSkyScraper(1, new Point(335, 225));
        spawnSkyScraper(2, new Point(45, 350));
        spawnSkyScraper(3, new Point(840, 75));
        lastSkyScraper = skyScrapers.size();

        spawnRefuelingBlimp();
        spawnRefuelingBlimp();

        spawnInitialNonPlayerHelicoptersInCircle(3,
                initialSkyScraper.getLocation(), 200, 0);

        // spawns player at skyscraper with sequenceNumber 1
        spawnPlayer(initialSkyScraper.getLocation());

        // hard coded initial bird and refueling blimp spawns, change this
        // later to dynamic spawns
        spawnBird();
        spawnBird();
    }

    public SkyScraper spawnSkyScraper(int sequenceNumber, Point location) {
        SkyScraper skyScraper = new SkyScraper(sequenceNumber, location);
        skyScrapers.add(skyScraper);
        gameObjectCollection.add(skyScraper);

        return skyScraper;
    }

    public Helicopter spawnPlayer(Point location) {
        player.setLocation(location);
        player.setSpeed(10);

        gameObjectCollection.add(player);
        glassCockpit.update();

        return player;
    }

    private void spawnInitialNonPlayerHelicoptersInCircle(
            int amount, Point center, int radius, int deviation) {
        for (int i = 0; i < amount; i++) {
            int negative = randomInt(0, 1) == 0 ? 1 : -1;
            int randomizedRadius = radius + deviation * negative;
            int theta = randomInt(0, 359);

            int x = (int) (center.getX() + randomizedRadius *
                    Math.sin(Math.toRadians(theta)));
            int y = (int) (center.getY() + randomizedRadius *
                    Math.cos(Math.toRadians(theta)));

            spawnNonPlayerHelicopter(new Point(x, y));
        }
    }

    // spawns a non-player helicopter at the first SkyScraper
    public NonPlayerHelicopter spawnNonPlayerHelicopter(Point location) {
        NonPlayerHelicopter helicopter =
                new NonPlayerHelicopter(randomInt(80, 95), location);

        gameObjectCollection.add(helicopter);
        return helicopter;
    }

    // spawns a bird at random location with a random size, speed, and heading
    public Bird spawnBird() {
        int randomSize = randomInt(40, 60);
        Point randomLocation = new Point(randomInt(gameWorldWidth),
                randomInt(gameWorldHeight));
        int randomSpeed = randomInt(5, 10);
        int randomHeading = randomInt(359);

        Bird bird = new Bird(randomSize, randomLocation, randomSpeed,
                randomHeading);
        gameObjectCollection.add(bird);

        return bird;
    }

    // spawns a refueling blimp at a random location with a random size
    public RefuelingBlimp spawnRefuelingBlimp() {
        int randomSize = randomInt(70, 100);
        Point randomLocation = new Point(randomInt(gameWorldWidth),
                randomInt(gameWorldHeight));

        RefuelingBlimp refuelingBlimp =
                new RefuelingBlimp(randomSize, randomLocation);
        gameObjectCollection.add(refuelingBlimp);

        return refuelingBlimp;
    }

    public void accelerate() {
        int accelerateAmount = 5;

        System.out.println("speed increased by " + accelerateAmount);

        player.accelerate(accelerateAmount);

        player.fadeColor(20);
    }

    public void brake() {
        int brakeAmount = 5;

        System.out.println("speed decreased by " + brakeAmount);

        player.speedDecrease(brakeAmount);

        player.resetColor();
    }

    public void changeStickAngleLeft() {
        System.out.println("stick angle changed to the left by 5 degrees");

        player.changeStickAngle(-5);
    }

    public void changeStickAngleRight() {
        System.out.println("stick angle changed to the right by 5 degrees");

        player.changeStickAngle(5);
    }

    // adds damage to player heli and reduces red color of player heli
    public void collisionWithHelicopter() {
        System.out.println("collision with other helicopter");

        player.applyDamage(helicopterCollisionDamage);
        player.collisionWithHelicopterColorChange(-10);
        glassCockpit.update();
    }

    // dialog.show() is overdone, fix in the future
    public void collisionWithSkyScraper() {
        pause();

        Dialog dialog = new Dialog("Sequence Number");
        TextField textField = new TextField();

        Button okButton = new Button("Ok");
        okButton.addActionListener((e) -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(BorderLayout.NORTH, textField);
        dialog.add(BorderLayout.SOUTH, okButton);

        dialog.show();

        int sequenceNumber = textField.getAsInt(-1);

        if (sequenceNumber < 0 || sequenceNumber > 9) {
            System.out.println("Sequence number must be a positive integer " +
                    "between 1 and 9.");
            return;
        }

        System.out.println("collision with SkyScraper #" + sequenceNumber);

        player.collisionWithSkyScraper(sequenceNumber);
        glassCockpit.update();
        pause();
    }

    // player heli collides with a refueling blimp and is refueled based on
    // the blimp's capacity; the blimp then has its color changed and spawns
    // a new blimp
    // the way it finds the blimp is bad, change in the future
    public void collisionWithRefuelingBlimp() {
        System.out.println("collision with refueling blimp");

        RefuelingBlimp blimp = null;
        for (int i = 0; i < gameObjectCollection.size(); i++) {
            if (gameObjectCollection.get(i) instanceof RefuelingBlimp) {
                blimp = (RefuelingBlimp) gameObjectCollection.get(i);
            }
        }

        player.fuelIncrease(blimp.getCapacity());
        blimp.fadeColor(50);
        blimp.emptyFuel();
        spawnRefuelingBlimp();
        glassCockpit.update();
    }

    // player heli collides with a bird and the player's color is changed and
    // takes damage
    // the way it finds the bird is bad, change in the future
    public void collisionWithBird() {
        System.out.println("collision with bird");

        Bird bird = null;
        for (int i = 0; i < gameObjectCollection.size(); i++) {
            if (gameObjectCollection.get(i) instanceof Bird)
                bird = (Bird) gameObjectCollection.get(i);
        }

        player.applyDamage(helicopterCollisionDamage / 2);
        player.collisionWithBirdColorChange(helicopterCollisionColorChange);
        glassCockpit.update();
    }

    private void wrapPlayer() {
        if (player.getLocation().getX() < 0)
            player.setLocation(
                    new Point(gameWorldWidth, player.getLocation().getY()));
        else if (player.getLocation().getX() > gameWorldWidth)
            player.setLocation(new Point(0, player.getLocation().getY()));
        else if (player.getLocation().getY() > gameWorldHeight)
            player.setLocation(new Point(player.getLocation().getX(), 0));
        else if (player.getLocation().getY() < 0)
            player.setLocation(
                    new Point(player.getLocation().getX(), gameWorldHeight));
    }

    // increments the game clock and updates every GameObject in the GameWorld
    public void tick() {
        //System.out.println("tick tock");

        checkMilestones();

        // all movable objects update their positions based on heading and speed
        for (int i = 0; i < gameObjectCollection.size(); i++) {
            if (gameObjectCollection.get(i) instanceof Bird) {
                Bird bird = (Bird) gameObjectCollection.get(i);
                bird.randomizeFlightPath(gameWorldWidth, gameWorldHeight);
            }

            if (gameObjectCollection.get(i) instanceof Movable) {
                wrapPlayer();
                Movable movableObject = (Movable) gameObjectCollection.get(i);
                movableObject.move(timeBetweenTicks);
            }

            if (gameObjectCollection.get(i) instanceof NonPlayerHelicopter) {
                NonPlayerHelicopter nph =
                        (NonPlayerHelicopter) gameObjectCollection.get(i);
                nph.unlimitedFuelUpdate();
            }
        }

        // updates the player's heading if the stick angle is being used
        if (player.getStickAngle() != 0) {
            player.changeHeading();
        }

        player.fuelDecrease();
        glassCockpit.update();
        mapView.update();
    }

    public void resetGame() {
        gameObjectCollection = new ArrayList<GameObject>();
        mapView.setGameObjectCollection(gameObjectCollection);
        initializeSpawns();
        player.reset();

        glassCockpit.update();
        mapView.update();
    }

    // checks to see if the player has died or has won
    public void checkMilestones() {
        if (player.getLastSkyScraperReached() >= lastSkyScraper)
            win();

        for (int i = 0; i < gameObjectCollection.size(); i++) {
            if (gameObjectCollection.get(i) instanceof NonPlayerHelicopter) {
                NonPlayerHelicopter nph =
                        (NonPlayerHelicopter) gameObjectCollection.get(i);

                if (nph.getLastSkyScraperReached() >= lastSkyScraper)
                    gameOverNPHReachedLastSkyScraper();
            }
        }

        if (player.isAtMaxDamage()) {
            reduceLives();

            if (!hasLives()) {
                gameOverDamage();
            } else {
                System.out.println("you have lost a life");
                resetGame();
            }
        }
    }

    public void gameOverDamage() {
        System.out.println("Game over, better luck next time!");
        System.exit(0);
    }

    public void gameOverNPHReachedLastSkyScraper() {
        System.out.println("Game over, a non-player helicopter wins!");
        System.exit(0);
    }

    public void win() {
        System.out.println("Game over, you win! Total time: " +
                glassCockpit.getElapsedTime());
        System.exit(0);
    }

    public void displayPlayerHeli() {
        System.out.println("lives=" + getLives() + " clock=" +
                glassCockpit.getElapsedTime() + " " + "SkyScraper#=" +
                player.getLastSkyScraperReached() + " " + "fuelLevel=" +
                player.getFuelLevel() + " damageLevel=" +
                player.getDamageLevel());
    }

    // displays info about every GameObject in the GameWorld
    public void displayMap() {
        for (int i = 0; i < gameObjectCollection.size(); i++) {
            System.out.println(gameObjectCollection.get(i).toString());
        }
    }

    public void changeStrategies() {
        System.out.println("strraty");
    }

    public void exit() {
        pause();

        if (Dialog.show("Exit","Are you sure you want to exit?","Yes","No")) {
            System.exit(0);
        }

        pause();
    }

    // displays information about the program
    public void aboutInfo() {
        pause();

        Dialog dialog = new Dialog("About", new BoxLayout(BoxLayout.Y_AXIS));
        Button button = new Button("Ok");
        button.addActionListener((e) -> dialog.dispose());

        dialog.add("Steven Maggs");
        dialog.add("CSC-133 Object-Oriented Computer Graphics Programming");
        dialog.add("v2");
        dialog.add(button);

        dialog.show();

        pause();
    }

    // displays keybindings
    public void helpInfo() {
        pause();

        Dialog dialog = new Dialog("Keybinds", new BoxLayout(BoxLayout.Y_AXIS));
        Button button = new Button("Ok");
        button.addActionListener((e) -> dialog.dispose());

        dialog.add("accelerate: a");
        dialog.add("brake: b");
        dialog.add("left turn: l");
        dialog.add("right turn: r");
        dialog.add("collide with nPH: n");
        dialog.add("collide with skyscraper: s");
        dialog.add("collide with e-blimp: e");
        dialog.add("collide with g-oony bird: g");
        dialog.add("exit: x");
        dialog.add(button);

        dialog.show();

        pause();
    }

    public void pause() {
        if (!isPaused) {
            isPaused = true;
            game.pause();
        }
        else
        {
            isPaused = false;
            game.unpause();
        }
    }

    public void reduceLives() {
        if (lives > 0)
            lives--;
    }

    public boolean hasLives() {
        return (lives > 0);
    }

    public int getLives() {
        return this.lives;
    }

    public void setGameWorldDimensions(int width, int height) {
        this.gameWorldWidth = width;
        this.gameWorldHeight = height;
    }

    public MapView getMapView() {
        return mapView;
    }

    public GlassCockpit getGlassCockpit() {
        return glassCockpit;
    }

    private int randomInt(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }

    private int randomInt(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min);
    }

    private ArrayList<SkyScraper> getSkyScrapers() {
        return this.skyScrapers;
    }
}