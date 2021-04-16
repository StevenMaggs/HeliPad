package org.csc133.a1;

// a fixed object that cannot change its location, inherited from GameObject
public abstract class Fixed extends GameObject {
    public Fixed(int initialSize, int initialColor, Point initialLocation) {
        super(initialSize, initialColor, initialLocation);
    }

    @Override
    public void setLocation(Point newLocation) {
        return;
    }
}
