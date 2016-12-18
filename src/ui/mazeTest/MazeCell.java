package ui.mazeTest;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MazeCell {

    private static final int XSIZE = 10;
    private static final int YSIZE = 10;
    private static final int MARGINTOP = 90;
    private static final int MARGINLEFT = 30;
    public final boolean ifWay;
    final Position cellPosition;
    private final Color cellColor;
    private boolean mark = true;
    private boolean ifVisit = false;
    private boolean footPrint = false;

    public MazeCell(int x, int y, char symbol) {
        cellPosition = new Position(x, y);
        if (symbol != ' ') {
            ifWay = false;
            cellColor = Color.black;
        } else {
            ifWay = true;
            cellColor = Color.white;
        }
    }

    public boolean getIfVisit() {
        return ifVisit;
    }

    public void setIfVisit(boolean state) {
        ifVisit = state;
    }

    public Color getColor() {

        if (ifVisit) {
            return Color.blue;
        } else if (footPrint) {
            return Color.green;
        } else if (!mark) {
            return Color.red;
        } else {
            return cellColor;
        }

    }

    public Rectangle2D getShape() {
        int x = cellPosition.getX() * XSIZE + MARGINLEFT;
        int y = cellPosition.getY() * YSIZE + MARGINTOP;
        return new Rectangle2D.Double(x, y, XSIZE, YSIZE);
    }

    public boolean getMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public boolean getFootprint() {
        return footPrint;
    }

    public void setFootprint(boolean footprint) {
        this.footPrint = footprint;
    }
}
