package tetris.components;

import javafx.scene.paint.Color;

public class ColoredPoint extends Point {
    private Color color;
    private Color borderColor;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public ColoredPoint(int x, int y, Color color, Color borderColor) {
        super(x, y);
        this.color = color;
        this.borderColor = borderColor;
    }

    public Color getColor() {
        return color;
    }

    public Color getBorderColor() {
        return borderColor;
    }
    
    
}
