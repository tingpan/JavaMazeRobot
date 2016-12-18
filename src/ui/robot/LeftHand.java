package ui.robot;

import ui.exceptions.RobotMoveException;
import ui.mazeTest.Maze;


public final class LeftHand extends Robot {

    public LeftHand() {
        name = "Left Hand Robot";
    }

    @Override
    public void move(Maze maze) throws RobotMoveException {
        currentCell = maze.getCell(location);
        surroundCell = surround(maze);
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
