package org.csc133.a1;

public class Point {
    private float x = 0;
    private float y = 0;

    public Point(float newX, float newY) {
        x = newX;
        y = newY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float newX) {
        x = newX;
    }

    public void setY(float newY) {
        y = newY;
    }

    @Override
    public String toString() {
        float roundedX = (float) (Math.round(x * 10.0) / 10.0);
        float roundedY = (float) (Math.round(y * 10.0) / 10.0);

        return roundedX + "," + roundedY;
    }
}
