package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

public class ClockComponent extends Component {
    Image[] digitImages = new Image[10];

    private int originalPrimaryLedColor;
    private int primaryLedColor = originalPrimaryLedColor;
    private int sizeScalingFactor = 3;
    private int numDigitsShowing;
    private int[] colorSwapTriggers;
    private int[] colorSwapColors;

    Image[] clockDigits;

    public ClockComponent(int numDigitsShowing, int[] colorSwapTriggers,
                          int[] colorSwapColors) {
        this.colorSwapTriggers = colorSwapTriggers;
        this.colorSwapColors = colorSwapColors;

        init(numDigitsShowing);
    }

    private void init(int numDigitsShowing) {
        this.numDigitsShowing = numDigitsShowing;
        this.clockDigits = new Image[numDigitsShowing];

        try {
            for (int i = 0; i < 10; i++)
                digitImages[i] = Image.createImage("/LED_digit_" + i + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numDigitsShowing; i++)
            clockDigits[i] = digitImages[0];
    }

    public void setCurrentValue(int value) {
        setValue(value);

        if (colorSwapTriggers != null)
            updateColors(value);
    }

    private void setValue(int value) {
        for (int i = 0; i < numDigitsShowing; i++) {
            clockDigits[numDigitsShowing - 1 - i] =
                    digitImages[value % 10];

            value /= 10;
        }
    }

    private void updateColors(int value) {
        for (int i = colorSwapTriggers.length - 1; i >= 0; i--) {
            primaryLedColor = originalPrimaryLedColor;

            if (value >= colorSwapTriggers[i]) {
                primaryLedColor = colorSwapColors[i];
                break;
            }
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

    public boolean animate(int value) {
        setCurrentValue(value);
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

        int clockWidth = clockDigits[0].getWidth() *
                numDigitsShowing / sizeScalingFactor;

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), clockWidth, getHeight());

        g.setColor(primaryLedColor);
        g.fillRect(getX(), getY(),
                clockWidth - sizeScalingFactor, displayDigitHeight);

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

    public void setLedColor(int primaryLedColor) {
        this.originalPrimaryLedColor = primaryLedColor;
        this.primaryLedColor = primaryLedColor;
    }

    public int getPrimaryLedColor() {
        return this.primaryLedColor;
    }

    public int getSizeScalingFactor() {
        return this.sizeScalingFactor;
    }
}
