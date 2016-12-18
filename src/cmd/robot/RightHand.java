package cmd.robot;

import cmd.exceptions.RobotMoveException;
import cmd.maze.Maze;


public final class RightHand extends Robot {

    public RightHand() {
        name = "Right Hand Robot";
    }

    @Override
    public void move(Maze maze) throws RobotMoveException {

        currentCell = maze.getCell(location);
        surroundCell = surround(maze);

        //The sequence of direction for the cmd.ui.robot to choose is Right, Front, Left
        if (detect(surroundCell[3])) {
            turnRight();
        } else if (detect(surroundCell[0])) {

        } else if (detect(surroundCell[1])) {
            turnLeft();
        } else {
            turnBack();
        }
        leave(currentCell);
        goStraight();
        currentCell = maze.getCell(location);
        visit(currentCell);

    }
}
