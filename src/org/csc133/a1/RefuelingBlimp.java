package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;

public class RefuelingBlimp extends Fixed {
    private int capacity;
    private static int refuelingBLimpColor = ColorUtil.rgb(0, 255, 0);

    Image blimpImage;
    private int refuelingBlimpAlpha;

    public RefuelingBlimp(int initialSize, Point initialLocation) {
        super(initialSize, refuelingBLimpColor, initialLocation);

        capacity = initialSize;

        try {
            blimpImage = Image.createImage("/blimp.png");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public int getCapacity() {
        return capacity;
    }

    // changes color when out of fuel
    public void emptyFuel() {
        capacity = 0;
        setColor(150, 255, 125);
    }

    public void fadeColor(int amount) {
        refuelingBlimpAlpha += amount;

        if (refuelingBlimpAlpha > 255)
            refuelingBlimpAlpha = 255;
    }

    public void resetColor() {
        refuelingBlimpAlpha = 0;
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.drawImage(blimpImage,
                (int) (containerOrigin.getX() + getLocation().getX() -
                        getSize() / 4),
                (int) (containerOrigin.getY() + getLocation().getY() -
                        getSize() / 2),
                getSize() / 2, getSize());
        g.setColor(ColorUtil.BLACK);
        g.drawString(String.valueOf(capacity),
                (int) (containerOrigin.getX() + getLocation().getX()),
                (int) (containerOrigin.getY() + getLocation().getY()));
        g.setAlpha(refuelingBlimpAlpha);
        g.setColor(refuelingBLimpColor);
        g.fillArc((int) (containerOrigin.getX() + getLocation().getX() -
                        getSize() / 4),
                (int) (containerOrigin.getY() + getLocation().getY() -
                        getSize() / 2),
                getSize() / 2, getSize(), 0, 360);
        g.setAlpha(255);
    }

    @Override
    public String toString() {
        String s = "RefuelingBlimp: " + super.toString();
        return s + " capacity=" + capacity;
    }
}
