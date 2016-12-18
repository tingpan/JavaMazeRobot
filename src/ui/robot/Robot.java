package ui.robot;

import ui.exceptions.RobotMoveException;
import ui.mazeTest.Direction;
import ui.mazeTest.Maze;
import ui.mazeTest.MazeCell;
import ui.mazeTest.Position;


public abstract class Robot {

    protected Position location;
    protected Direction faceDir = Direction.SOUTH;
    protected MazeCell[] surroundCell;
    protected MazeCell currentCell;
    protected String name;

    public abstract void move(Maze maze) throws RobotMoveException;

    public void intoMaze(Maze maze) throws RobotMoveException {
        location = new Position(1, 1);
        MazeCell cell = maze.getCell(location);
        visit(cell);
    }

    protected void turnLeft() {
        int newDir = (faceDir.ordinal() + 1) % 4;
        faceDir = Direction.values()[newDir];
    }

    protected void turnRight() {
        int newDir = (faceDir.ordinal() - 1 + 4) % 4;
        faceDir = Direction.values()[newDir];

    }

    protected void turnBack() {
        turnRight();
        turnRight();
    }

    protected void goStraight() {
        location = location.nextPosition(faceDir);
        location = location.nextPosition(faceDir);
    }


    protected MazeCell[] surround(Maze maze) throws RobotMoveException {
        turnLeft();
        MazeCell leftCell = maze.getCell(location.nextPosition(faceDir));
        turnLeft();
        MazeCell backCell = maze.getCell(location.nextPosition(faceDir));
        turnLeft();
        MazeCell rightCell = maze.getCell(location.nextPosition(faceDir));
        turnLeft();
        MazeCell frontCell = maze.getCell(location.nextPosition(faceDir));
        MazeCell[] result = {frontCell, leftCell, backCell, rightCell};
        return result;
    }

    protected boolean detect(MazeCell c) {
        return c.ifWay;
    }

    public String echoName() {
        return name;
    }

    public boolean arrive(Position p) {
        return p.getX() == getLocation().getX() && p.getY() == getLocation().getY();
    }

    protected void visit(MazeCell cell) {
        cell.setIfVisit(true);
    }

    protected void leave(MazeCell cell) {
        cell.setIfVisit(false);
    }

    public Position getLocation() {
        return location;
    }
}
