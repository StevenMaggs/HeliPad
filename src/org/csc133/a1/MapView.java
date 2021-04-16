package org.csc133.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;

import java.util.ArrayList;

public class MapView extends Container {
    ArrayList<GameObject> gameObjectCollection;

    public MapView(ArrayList<GameObject> gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    public void update() {
        this.repaint();
    }

    protected Dimension calcPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

    public void setGameObjectCollection(ArrayList<GameObject> collection) {
        this.gameObjectCollection = collection;
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < gameObjectCollection.size(); i++) {
            gameObjectCollection.get(i).draw(g, new Point(getX(), getY()));
        }
    }
}
