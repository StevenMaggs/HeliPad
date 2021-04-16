package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;

public class SkyScraper  extends Fixed {
    private int sequenceNumber;
    private static final int skyScraperSize = 70;
    private static int skyScraperColor = ColorUtil.rgb(0, 0, 255);

    Image[] skyScraperImages = new Image[11];

    public SkyScraper(int initialSequenceNumber, Point initialLocation) {
        super(skyScraperSize, skyScraperColor, initialLocation);

        sequenceNumber = initialSequenceNumber;

        try {
            for (int i = 0; i < skyScraperImages.length; i++)
                skyScraperImages[i] =
                        Image.createImage("/skyScraper_" + i + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(ColorUtil.LTGRAY);
        g.fillRect(
                (int) (containerOrigin.getX() + getLocation().getX() - getSize() / 2 + 1),
                (int) (containerOrigin.getY() + getLocation().getY() - getSize() / 2 + 1),
                getSize() - 1, getSize() - 1);
        g.drawImage(skyScraperImages[sequenceNumber],
                (int) (containerOrigin.getX() + getLocation().getX() - getSize() / 2),
                (int) (containerOrigin.getY() + getLocation().getY() - getSize() / 2),
                getSize(), getSize());
    }

    @Override
    public String toString() {
        String s = "SkyScraper: " + super.toString();
        return s + " seqNum=" + sequenceNumber;
    }
}
