package cmd.robot;

import cmd.exceptions.RobotMoveException;
import cmd.maze.Maze;
import cmd.maze.MazeCell;
import cmd.maze.Position;


public abstract class Robot {

    //can be extended
    protected Position location;
    protected Direction faceDir = Direction.SOUTH;
    protected MazeCell[] surroundCell;
    protected MazeCell currentCell;
    protected String name;

    //should be override
    public abstract void move(Maze maze) throws RobotMoveException;


    //cmd.ui.robot into a cmd.maze
    public void intoMaze(Maze maze) throws RobotMoveException {
        location = new Position(1, 1);
        MazeCell cell = maze.getCell(location);
        visit(cell);
    }


    //because trun left is in clockwise, turn left equals to the enum value plus one.
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


    //move position twice because the cmd.maze has the walls.
    protected void goStraight() {
        location = location.nextPosition(faceDir);
        location = location.nextPosition(faceDir);
    }


    //get surround cells
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


    //whether a cell is a way
    protected boolean detect(MazeCell c) {
        return c.isMovable;
    }


    //print the cmd.ui.robot's name
    public void echoName() {
        System.out.println(name);
    }


    //whether the cmd.ui.robot is arrive a position
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
