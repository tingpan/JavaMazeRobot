package cmd.maze;

import cmd.robot.Direction;

public class Position {

    private int x, y;

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    //return the next postion depends on different directions
    public Position nextPosition(Direction d) {
        Position newP;
        switch (d) {
            case NORTH:
                newP = new Position(x, y - 1);
                break;
            case EAST:
                newP = new Position(x + 1, y);
                break;
            case SOUTH:
                newP = new Position(x, y + 1);
                break;
            case WEST:
                newP = new Position(x - 1, y);
                break;
            default:
                newP = new Position(1, 1);
        }
        return newP;
    }

}
