package cmd.robot;

import cmd.exceptions.RobotMoveException;
import cmd.maze.Maze;
import cmd.maze.MazeCell;

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

        //if the direction is more than two
        //which means not only the back direction and another direction
        //it is a junction, the cmd.ui.robot should randomly choose a direction
        if (ways.size() > 2) {
            int index = die(ways);
            System.out.println("index:" + index);
            faceDir = ways.get(index);
        }

        //if there is one direction can go, just go to this direction
        else if (ways.size() == 2) {
            for (Direction d : ways) {
                //not the back direction
                if (Math.abs(d.ordinal() - faceDir.ordinal()) != 2) {
                    faceDir = d;
                    break;
                }
            }
        }
        //if there is one direction(dead end),turn back
        else {
            faceDir = ways.get(0);
        }

        leave(currentCell);
        goStraight();
        currentCell = maze.getCell(location);
        visit(currentCell);

    }


    //to decide which way to go based on the amount of the way can go.
    protected int die(ArrayList<Direction> ways) {
        Random die = new Random();
        return die.nextInt(ways.size());
    }


    //if a way is not a wall, return the direction of the way.
    protected ArrayList<Direction> avaliableWays(MazeCell[] surrounds) {
        ArrayList<Direction> ways = new ArrayList<Direction>();
        for (int i = 0; i < surrounds.length; i++) {
            if (surrounds[i].isMovable) {
                int d = faceDir.ordinal();
                //math relation of the direction.
                Direction way = Direction.values()[(d + i) % 4];
                ways.add(way);
            }
        }
        return ways;
    }
}
