package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;

public class Helicopter extends Movable implements ISteerable {
    // indicates how the control stick is turned in relation to the front of
    // the heli, max 5 degrees change per tick, max +/- 40 degrees total at once
    private int stickAngle;
    private int stickAngleMaxHeadingChangePerTick = 5;
    private int maxSpeedOriginal = 50;
    private int maxSpeed = maxSpeedOriginal; // speed can't exceed this
    private int initialFuelLevel = 9999;
    private int fuelLevel = initialFuelLevel; // 0 fuelLevel = 0 speed & turning
    private int fuelConsumptionRate = 1; // fuel consumed per tick
    private int damageLevel; // maxSpeed=damageLevel/maximumDamageLevel*maxSpeed
    private final int maxDamageLevel = 99;
    private int lastSkyScraperReached = 1;
    private static int helicopterSize = 100;
    private static int defaultHelicopterColor = ColorUtil.rgb(255, 0, 0);
    private static Helicopter instance = new Helicopter(helicopterSize);

    private Image helicopterBodyImage;
    private Image helicopterRotorImage;
    private Image helicopterBladeImage;
    private int helicopterBladeAlpha = 0;

    protected Helicopter(int initialSize) {
        super(initialSize, defaultHelicopterColor, new Point(0, 0));

        try {
            helicopterBodyImage = Image.createImage("/helicopterBody.png");
            helicopterBladeImage = Image.createImage("/helicopterBlade.png");
            helicopterRotorImage = Image.createImage("/helicopterRotor.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        stickAngle = 0;
        maxSpeed = maxSpeedOriginal;
        fuelLevel = initialFuelLevel;
        damageLevel = 0;
        resetColor();
        resetHeading();
    }

    // if fuel = 0, heli can't turn or move
    public void fuelDecrease() {
        fuelLevel -= fuelConsumptionRate;

        if (fuelLevel <= 0) {
            setSpeed(0);
            stickAngle = 0;
            fuelLevel = 0;
        }
    }

    public void fuelIncrease(int amount) {
        fuelLevel += amount;
    }

    public void accelerate(int amount) {
        if (fuelLevel <= 0 || damageLevel == maxDamageLevel)
            return;

        speedIncrease(amount);

        if (getSpeed() > maxSpeed)
            setSpeed(maxSpeed);
    }

    // max speed is reduced based on percentage of damage taken
    public void applyDamage(int damageAmount) {
        damageLevel += damageAmount;
        if (damageLevel > maxDamageLevel)
            damageLevel = maxDamageLevel;

        updateSpeed();
    }

    public void updateSpeed() {
        maxSpeed = (int)(maxSpeedOriginal - ((float) damageLevel /
                maxDamageLevel * maxSpeedOriginal));

        if (getSpeed() > maxSpeed)
            setSpeed(maxSpeed);
    }

    // make the color of the helicopter less red
    public void collisionWithHelicopterColorChange(int color) {
        int c = getColor();
        int r = ColorUtil.red(c) + color;
        int g = ColorUtil.green(c);
        int b = ColorUtil.blue(c);

        if (r < 0)
            r = 0;
        else if (r > 255)
            r = 255;

        setColor(r, g, b);
    }

    // make the color of the helicopter lighter red
    public void collisionWithBirdColorChange(int color) {
        int c = getColor();
        int r = ColorUtil.red(c);
        int g = ColorUtil.green(c) + color;
        int b = ColorUtil.blue(c);

        if (g < 0)
            g = 0;
        else if (g > 255)
            g = 255;

        setColor(r, g, b);
    }

    public void collisionWithSkyScraper(int sequenceNumber) {
        if (sequenceNumber > lastSkyScraperReached)
            lastSkyScraperReached = sequenceNumber;
    }

    public void changeStickAngle(int amount) {
        stickAngle += amount;

        if (stickAngle > 40)
            stickAngle = 40;
        if (stickAngle < -40)
            stickAngle = -40;
    }

    public int getStickAngle() {
        return stickAngle;
    }

    public int getLastSkyScraperReached() {
        return lastSkyScraperReached;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public boolean isAtMaxDamage() {
        return damageLevel == maxDamageLevel;
    }

    public static Helicopter getInstance() {
        return instance;
    }

    public void fadeColor(int amount) {
        helicopterBladeAlpha += amount;

        if (helicopterBladeAlpha > 255)
            helicopterBladeAlpha = 255;
    }

    public void resetColor() {
        helicopterBladeAlpha = 0;
    }

    // changes the heli's heading over several ticks based on the stick angle
    // only a certain amount of change can be applied to the heading each tick
    // if there is no fuel, then the heli cannot turn
    @Override
    public void changeHeading() {
        if (fuelLevel <= 0 || damageLevel >= maxDamageLevel)
            return;

        if (stickAngle > 5) {
            stickAngle -= stickAngleMaxHeadingChangePerTick;
            setHeading(stickAngleMaxHeadingChangePerTick);
        } else if (stickAngle < -5) {
            stickAngle += stickAngleMaxHeadingChangePerTick;
            setHeading(-stickAngleMaxHeadingChangePerTick);
        } else {
            if (stickAngle > 0)
                setHeading(stickAngle);
            else
                setHeading(stickAngle);

            stickAngle = 0;
        }
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.drawImage(helicopterBodyImage,
                (int) (containerOrigin.getX() + getLocation().getX() -
                        getSize() / 2),
                (int) (containerOrigin.getY() + getLocation().getY() -
                        getSize() / 2),
                getSize(), getSize());
        g.drawImage(helicopterBladeImage,
                (int) (containerOrigin.getX() + getLocation().getX() -
                        getSize() / 2),
                (int) (containerOrigin.getY() + getLocation().getY() -
                        getSize() / 4),
                getSize(), getSize() / 20);
        g.setAlpha(helicopterBladeAlpha);
        g.setColor(defaultHelicopterColor);
        g.fillRect((int) (containerOrigin.getX() + getLocation().getX() -
                        getSize() / 2),
                (int) (containerOrigin.getY() + getLocation().getY() -
                        getSize() / 4),
                getSize(), getSize() / 20);
        g.setAlpha(255);
        g.drawImage(helicopterRotorImage,
                (int) (containerOrigin.getX() + getLocation().getX() -
                        getSize() / 2),
                (int) (containerOrigin.getY() + getLocation().getY() -
                        getSize() / 2),
                getSize(), getSize());
    }

    @Override
    public String toString() {
        String s = "Helicopter: " + super.toString();
        return s + " maxSpeed=" + maxSpeed + " stickAngle=" + stickAngle +
                " fuelLevel=" + fuelLevel + " damageLevel=" + damageLevel;
    }
}
