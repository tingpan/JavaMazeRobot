package cmd.robot;

import cmd.exceptions.RobotMoveException;
import cmd.maze.Maze;
import cmd.maze.MazeCell;

import java.util.ArrayList;

public final class RandomMemory extends RandomRobot {

    public RandomMemory() {
        name = "Random Memory Robot";
    }

    @Override
    public void move(Maze maze) throws RobotMoveException {
        MazeCell currentCell = maze.getCell(location);
        surroundCell = surround(maze);

        //store the directions it can move toward to.
        ArrayList<Direction> ways = avaliableWays(surroundCell);
        boolean wrongWay = false;

        //if there is a junction, throw the die
        //because the back way has the footprint, ways > 1 can represent a junction
        if (ways.size() > 1) {
            faceDir = ways.get(die(ways));
        }

        //if there is only one road, than visit this road.
        else if (ways.size() == 1) {
            faceDir = ways.get(0);
        }

        //if all roads are wrong, then find the way which have footprint(entrance of the junction)
        //then it is a wrong way.
        else {
            findWay(surroundCell);
            wrongWay = true;
        }

        //if wrongway, clear the foot print and mark this way.
        //because there is a wall between two mazecells,
        //so should mark and clear both the current cell and the front cell.
        if (wrongWay) {

            mark(currentCell);
            mark(maze.getCell(location.nextPosition(faceDir)));
            clearFootPrint(currentCell);
            clearFootPrint(maze.getCell(location.nextPosition(faceDir)));
        }

        //if not the wrong way, set footprint
        else {
            setFootPrint(currentCell);
            setFootPrint(maze.getCell(location.nextPosition(faceDir)));
        }

        leave(currentCell);
        goStraight();
        currentCell = maze.getCell(location);
        visit(currentCell);
    }


    private void mark(MazeCell cell) {
        cell.setMark(false);
    }

    private void clearFootPrint(MazeCell cell) {
        cell.setFootprint(false);
    }

    private void setFootPrint(MazeCell cell) {
        cell.setFootprint(true);
    }

    @Override
    //if a way have no footprint(never been visited), is not a wall and not a wrongway
    //return the direction.
    protected ArrayList<Direction> avaliableWays(MazeCell[] surrounds) {
        ArrayList<Direction> ways = new ArrayList<Direction>();
        for (int i = 0; i < surrounds.length; i++) {
            if (surrounds[i].isMovable && !surrounds[i].getFootprint() && surrounds[i].getMark()) {
                int d = faceDir.ordinal();
                //mathmatic relations between directions.
                Direction way = Direction.values()[(d + i) % 4];
                ways.add(way);
            }
        }
        return ways;
    }

    //find the way which has the footprint
    private void findWay(MazeCell[] surrounds) {
        for (int i = 0; i < surrounds.length; i++) {
            if (surrounds[i].isMovable && surrounds[i].getFootprint()) {
                int d = faceDir.ordinal();
                faceDir = Direction.values()[(d + i) % 4];
            }
        }
    }

}
