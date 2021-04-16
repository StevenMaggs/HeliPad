package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;

public abstract class GameObject implements IDrawable {
    private int size;
    private int color;
    private Point location = new Point(0, 0);

    public GameObject(int initialSize, int initialColor, Point initialLocation) {
        size = initialSize;
        color = initialColor;
        location = initialLocation;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int r, int g, int b) {
        color = ColorUtil.rgb(r, g, b);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point newLocation) {
        location = newLocation;
    }

    @Override
    public String toString() {
        return "loc=" + location.toString() + " [" + "color=" +
                ColorUtil.red(color) + "," + ColorUtil.green(color) + "," +
                ColorUtil.blue(color) + "]" + " size=" + size;
    }
}
