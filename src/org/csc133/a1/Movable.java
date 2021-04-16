package org.csc133.a1;

// a movable object that has a heading and speed that determines its
// direction and movement, inherited from GameObject
public abstract class Movable extends GameObject {
    private int heading; // compass angle in degrees
    private int speed;

    public Movable(int initialSize, int initialColor, Point initialLocation) {
        super(initialSize, initialColor, initialLocation);
    }

    // update location based on current heading and speed
    public void move(int elapsedTime) {
        int theta = 90 - heading;
        float deltaX = (float) Math.cos(Math.toRadians(theta)) * speed *
                        (2f / elapsedTime);
        float deltaY = (float) Math.sin(Math.toRadians(theta)) * speed *
                        (2f / elapsedTime);

        Point newLocation = new Point(getLocation().getX() + deltaX,
                getLocation().getY() + deltaY);
        setLocation(newLocation);
    }

    public void speedIncrease(int amount) {
        speed += amount;
    }

    public void speedDecrease(int amount) {
        speed -= amount;
        if (speed < 0)
            speed = 0;
    }

    // heading loops around if it goes above 359 or below 0
    public void setHeading(int amount) {
        heading += amount;

        if (heading > 359)
            heading -= 360;
        else if (heading < 0)
            heading += 360;
    }

    public void setSpeed(int amount) {
        speed = amount;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHeading() {
        return heading;
    }

    public void resetHeading() {
        heading = 0;
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s + " heading=" + heading + " speed=" + speed;
    }
}
