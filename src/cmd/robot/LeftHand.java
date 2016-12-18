package cmd.robot;

import cmd.exceptions.RobotMoveException;
import cmd.maze.Maze;


public final class LeftHand extends Robot {

    public LeftHand() {
        name = "Left Hand Robot";
    }


    @Override
    public void move(Maze maze) throws RobotMoveException {

        currentCell = maze.getCell(location);
        surroundCell = surround(maze);

        //The sequence of direction for the cmd.ui.robot to choose is Left, Front, Right
        if (detect(surroundCell[1])) {
            turnLeft();
        } else if (detect(surroundCell[0])) {

        } else if (detect(surroundCell[3])) {
            turnRight();
        } else {
            turnBack();
        }
        leave(currentCell);

        goStraight();

        currentCell = maze.getCell(location);
        visit(currentCell);
    }


}
