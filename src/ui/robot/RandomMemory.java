package ui.robot;

import ui.exceptions.RobotMoveException;
import ui.mazeTest.Direction;
import ui.mazeTest.Maze;
import ui.mazeTest.MazeCell;

import java.util.ArrayList;

public final class RandomMemory extends RandomRobot {

    public RandomMemory() {
        name = "Random Memory Robot";
    }

    @Override
    public void move(Maze maze) throws RobotMoveException {
        MazeCell currentCell = maze.getCell(location);
        surroundCell = surround(maze);
        ArrayList<Direction> ways = avaliableWays(surroundCell);
        boolean wrongWay = false;

        if (ways.size() > 1) {
            faceDir = ways.get(die(ways));
        } else if (ways.size() == 1) {
            faceDir = ways.get(0);
        } else {
            findWay(surroundCell);
            wrongWay = true;
        }

        if (wrongWay) {

            mark(currentCell);
            mark(maze.getCell(location.nextPosition(faceDir)));
            clearFootPrint(currentCell);
            clearFootPrint(maze.getCell(location.nextPosition(faceDir)));
        } else {
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

    private ArrayList<Direction> avaliableWays(MazeCell[] surrounds) {
        ArrayList<Direction> ways = new ArrayList<Direction>();
        for (int i = 0; i < surrounds.length; i++) {
            if (surrounds[i].ifWay && !surrounds[i].getFootprint() && surrounds[i].getMark()) {
                int d = faceDir.ordinal();
                Direction way = Direction.values()[(d + i) % 4];
                ways.add(way);
            }
        }
        return ways;
    }

    private void findWay(MazeCell[] surrounds) {
        for (int i = 0; i < surrounds.length; i++) {
            if (surrounds[i].ifWay && surrounds[i].getFootprint()) {
                int d = faceDir.ordinal();
                faceDir = Direction.values()[(d + i) % 4];
            }
        }
    }

}
