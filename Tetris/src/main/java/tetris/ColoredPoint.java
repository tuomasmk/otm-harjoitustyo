package tetris;

import javafx.scene.paint.Color;
import tetris.Point;


public class ColoredPoint extends Point {
    private Color color;
    private Color borderColor;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Color getBorderColor() {
        return borderColor;
    }
    
    
}
