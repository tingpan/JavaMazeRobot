package cmd.maze;

import cmd.exceptions.RobotMoveException;
import cmd.robot.Robot;

public class GameManager {

    private Maze maze;
    private Robot robot;
    private Position lastPosition;
    private int step;

    // constructor
    public GameManager(Maze maze, Robot robot) {
        this.maze = maze;
        this.robot = robot;
        step = 0;
        lastPosition = robot.getLocation();
    }

    // put the cmd.ui.robot into the cmd.maze and loop until the cmd.ui.robot get the exit.
    public void gameStart() {

        try {
            robot.intoMaze(maze);

        } catch (RobotMoveException e1) {
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        }

        while (!robot.arrive(maze.getEXIT())) {
            // clear screen
            System.out.println("\u001b[2J");
            System.out.flush();

            // print Info
            robot.echoName();
            System.out.println("Move " + step);

            // cheating detect
            lastPosition = new Position(robot.getLocation().getX(), robot
                    .getLocation().getY());

            // cmd.ui.robot may out of bounds
            try {
                robot.move(maze);

            } catch (RobotMoveException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }

            // print the cmd.maze
            maze.printMaze();
            step++;

            // if cheating, terminate the game
            if (!notThroughWall() || !validPace() || !validPosition()) {
                System.out.println("You are cheating!");
                System.exit(-1);
            }

            // control the screen refresh
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        // get exist
        gameFinish();

    }

    private void gameFinish() {
        System.out.println("The cmd.ui.robot found the exit!!");
        System.exit(1);

    }

    // cheating detect.
    // if a move is more than two position, it is invalid
    private boolean validPace() {
        int xMove = Math.abs(lastPosition.getX() - robot.getLocation().getX());
        int yMove = Math.abs(lastPosition.getY() - robot.getLocation().getY());
        return (!(xMove > 2 || yMove > 2));
    }

    // if the cmd.ui.robot is in the wall, it is invalid
    private boolean validPosition() {
        MazeCell c;

        try {
            c = maze.getCell(robot.getLocation());
        } catch (RobotMoveException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return c.isMovable;
    }

    // if a move is through a wall, it is invalid
    private boolean notThroughWall() {
        int xCell = (lastPosition.getX() + robot.getLocation().getX()) / 2;
        int yCell = (lastPosition.getY() + robot.getLocation().getY()) / 2;
        Position pass = new Position(xCell, yCell);
        MazeCell c;

        try {
            c = maze.getCell(pass);
        } catch (RobotMoveException e) {
            e.printStackTrace();
            return false;
        }

        return c.isMovable;
    }

}
