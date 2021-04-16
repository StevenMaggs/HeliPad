package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.awt.*;
import java.io.IOException;

public class GameClockComponent extends Component {
    Image[] digitImages = new Image[10];
    Image[] dotDigitImages = new Image[10];
    Image colonImage;

    private int startTime = 0;
    private int pauseTime = 0;
    private boolean isPaused = false;
    private int primaryLedColor;
    private int secondaryLedColor;
    private int darkPrimaryLedColor = ColorUtil.rgb(255, 0, 0);
    private int darkSecondaryLedColor = ColorUtil.rgb(100, 0, 0);
    private int sizeScalingFactor = 3;
    private static final int timeWhenColorChanges = 5000;
    private static int MS_COLON_IDX = 2;
    private static int ST_DOT_IDX  = 5;
    private static final int numDigitsShowing = 6;

    Image[] clockDigits = new Image[numDigitsShowing];

    public GameClockComponent() {
        try {
            for (int i = 0; i < 10; i++) {
                digitImages[i] = Image.createImage("/LED_digit_" + i + ".png");
                dotDigitImages[i] = Image.createImage("/LED_digit_" + i +
                        "_with_dot.png");
            }

            colonImage = Image.createImage("/LED_colon.png");
        } catch (IOException e) { e.printStackTrace(); }

        for (int i = 0; i < numDigitsShowing; i++)
            clockDigits[i] = digitImages[0];

        clockDigits[MS_COLON_IDX] = colonImage;

        startTime = (int) System.currentTimeMillis();
    }

    private void setTime(int m, int s, int ms) {
        clockDigits[0] = digitImages[m / 10];
        clockDigits[1] = digitImages[m % 10];
        clockDigits[3] = digitImages[s / 10];
        clockDigits[4] = dotDigitImages[s % 10];
        clockDigits[5] = digitImages[(ms % 1000) / 100];
    }

    public void startElapsedTime() {
        if (!isPaused)
            return;

        startTime = (int) System.currentTimeMillis();
        isPaused = false;
    }

    public void stopElapsedTime() {
        if (isPaused)
            return;

        pauseTime += System.currentTimeMillis() - startTime;
        isPaused = true;
    }

    public void resetElapsedTime() {
        startTime = (int) System.currentTimeMillis();
        pauseTime = 0;
        isPaused = false;
        setCurrentTime();
    }

    public int getElapsedTime() {
        if (!isPaused)
            return (int) System.currentTimeMillis() - startTime + pauseTime;
        else
            return pauseTime;
    }

    private void setCurrentTime() {
        int elapsedMilliseconds = getElapsedTime();
        int elapsedSeconds = elapsedMilliseconds / 1000;
        int secondsDisplay = elapsedSeconds % 60;
        int elapsedMinutes = elapsedSeconds / 60;

        setTime(elapsedMinutes, secondsDisplay, elapsedMilliseconds);

        updateColors(elapsedMilliseconds);
    }

    private void updateColors(int milliseconds) {
        if (milliseconds >= timeWhenColorChanges) {
            primaryLedColor = darkPrimaryLedColor;
            secondaryLedColor = darkSecondaryLedColor;
        }
    }

    public void start() {
        getComponentForm().registerAnimated(this);
    }

    public void stop() {
        getComponentForm().deregisterAnimated(this);
    }

    public void laidOut() {
        this.start();
    }

    public boolean animate() {
        setCurrentTime();
        return true;
    }

    protected Dimension calcPreferredSize() {
        return new Dimension(
                clockDigits[0].getWidth() * numDigitsShowing /
                        sizeScalingFactor,
                clockDigits[0].getHeight() / sizeScalingFactor);
    }

    public void paint(Graphics g) {
        super.paint(g);

        int displayDigitWidth = clockDigits[0].getWidth() / sizeScalingFactor;
        int displayDigitHeight = getHeight();

        int clockWidth = clockDigits[0].getWidth() * numDigitsShowing / sizeScalingFactor;

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), clockWidth, getHeight());

        g.setColor(primaryLedColor);
        g.fillRect(getX(), getY(),
                clockWidth - sizeScalingFactor, displayDigitHeight);
        g.setColor(secondaryLedColor);
        g.fillRect(getX() + (numDigitsShowing - 1) * displayDigitWidth, getY(),
                displayDigitWidth, displayDigitHeight);

        for (int i = 0; i < numDigitsShowing; i++) {
            g.drawImage(
                    clockDigits[i],
                    getX() + i * displayDigitWidth,
                    getY(),
                    displayDigitWidth,
                    displayDigitHeight);
        }
    }

    public void setSizeScalingFactor(int size) {
        sizeScalingFactor = size;
    }

    public void setLedColor(int primaryLedColor, int secondaryLedColor) {
        this.primaryLedColor = primaryLedColor;
        this.secondaryLedColor = secondaryLedColor;
    }
}
