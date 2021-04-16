package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;

public class NonPlayerHelicopter extends Helicopter {
    private static int defaultNonPlayerHelicopterColor = ColorUtil.rgb(255, 0,
            0);
    private int maxDamageLevel = 50;
    private int damageLevel = 25;
    private INPHStrategy strategy;

    public NonPlayerHelicopter(int size, Point initialLocation) {
        super(size);
        setLocation(initialLocation);

        setColor(defaultNonPlayerHelicopterColor);
    }

    public void setNPHStrategy(INPHStrategy strategy) {
        this.strategy = strategy;
    }

    public void invokeStrategy(GameWorld gameWorld) {
        this.strategy.executeStrategy(gameWorld);
    }

    public void unlimitedFuelUpdate() {
        if (getFuelLevel() < 10)
            fuelIncrease(10000);
    }

    @Override
    public void applyDamage(int damageAmount) {
        damageLevel += damageAmount;

        if (damageLevel > maxDamageLevel)
            damageLevel = maxDamageLevel;
    }

    @Override
    public String toString() {
        String s = "Non-Player Helicopter: " + super.toString();
        return s + " strategy=" + strategy.toString();
    }
}