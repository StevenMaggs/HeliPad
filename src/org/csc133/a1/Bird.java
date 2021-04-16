package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;
import java.util.Random;

public class Bird extends Movable {
    private static final int birdColor = ColorUtil.rgb(255, 0, 255);

    private Image[] birdImages = new Image[2];

    // randomly changes heading while moving to not move in a straight line
    // if it hits the edge of world, change heading so it doesn't go outside
    // causes damage to heli if they collide (half of damage of heli+heli col)
    // can't change color once spawned
    // speed (5-10) and heading (0-359) are randomized at spawn

    public Bird(int initialSize, Point initialLocation, int initialSpeed,
                int initialDirection) {
        super(initialSize, birdColor, initialLocation);

        setSpeed(initialSpeed);

        try {
            for (int i = 0; i < birdImages.length; i++)
                birdImages[i] = Image.createImage("/bird_" + i + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void randomizeFlightPath(int maxWidth, int maxHeight) {
        // checks to see if the bird is out of bounds and flips its heading
        // if it is
        if (getLocation().getX() < 0 || getLocation().getX() > maxWidth ||
                getLocation().getY() < 0 || getLocation().getY() > maxHeight) {
            setHeading(180);
            return;
        }

        // randomizes the flight path by a small amount
        Random random = new Random();
        int randomSign = random.nextInt(2);
        if (randomSign == 0)
            randomSign = 1;
        else
            randomSign = -1;

        int randomHeading = 3 + random.nextInt(8);

        setHeading(randomSign * randomHeading);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.drawImage(birdImages[0],
                (int) (containerOrigin.getX() + getLocation().getX() - getSize() / 2),
                (int) (containerOrigin.getY() + getLocation().getY() - getSize() / 2),
                getSize(), getSize());
    }

    @Override
    public void setColor(int r, int g, int b) {
        return;
    }

    @Override
    public String toString() {
        return "Bird: " + super.toString();
    }
}
