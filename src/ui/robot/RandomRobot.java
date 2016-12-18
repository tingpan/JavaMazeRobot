package ui.robot;

import ui.exceptions.RobotMoveException;
import ui.mazeTest.Direction;
import ui.mazeTest.Maze;
import ui.mazeTest.MazeCell;

import java.util.ArrayList;
import java.util.Random;


public class RandomRobot extends Robot {

    public RandomRobot() {
        name = "Random Robot";
    }

    @Override
    public void move(Maze maze) throws RobotMoveException {
        MazeCell currentCell = maze.getCell(location);
        surroundCell = surround(maze);
        ArrayList<Direction> ways = avaliableWays(surroundCell);

        if (ways.size() > 2) {
            int index = die(ways);
            System.out.println("index:" + index);
            faceDir = ways.get(index);
        } else if (ways.size() == 2) {
            for (Direction d : ways) {
                if (Math.abs(d.ordinal() - faceDir.ordinal()) != 2) {
                    faceDir = d;
                    break;
                }
            }
        } else {
            faceDir = ways.get(0);
        }

        leave(currentCell);
        goStraight();
        currentCell = maze.getCell(location);
        visit(currentCell);

    }

    protected int die(ArrayList<Direction> ways) {
        Random die = new Random();
        return die.nextInt(ways.size());
    }

    private ArrayList<Direction> avaliableWays(MazeCell[] surrounds) {
        ArrayList<Direction> ways = new ArrayList<Direction>();
        for (int i = 0; i < surrounds.length; i++) {
            if (surrounds[i].ifWay) {
                int d = faceDir.ordinal();
                Direction way = Direction.values()[(d + i) % 4];
                ways.add(way);
            }
        }
        return ways;
    }
}
