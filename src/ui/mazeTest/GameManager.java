package ui.mazeTest;

import ui.exceptions.RobotMoveException;
import ui.robot.Robot;

import javax.swing.*;
import java.awt.*;


public class GameManager extends JPanel {

    private static final long serialVersionUID = 1L;
    public int runStatus = 0;
    int step;
    private Maze maze;
    private Robot robot;
    private Position lastPosition;

    public GameManager(Maze maze, Robot robot) {
        this.maze = maze;
        this.robot = robot;
        step = 0;
        lastPosition = robot.getLocation();
    }


    public void setMaze(Maze maze) {
        this.maze = maze;
    }


    public void setRobot(Robot robot) {
        this.robot = robot;
    }


    public void gameStart() {
        gameReset();
        try {
            robot.intoMaze(maze);
        } catch (RobotMoveException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        runStatus = 1;
    }


    public void gameRun() {
        lastPosition = new Position(robot.getLocation().getX(), robot.getLocation().getY());
        try {
            robot.move(maze);
        } catch (RobotMoveException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        step++;

        if (robot.arrive(maze.EXIT)) {
            runStatus = 2;
        }

        if (!notThroughWall() || !validPace()) {
            runStatus = -1;
        }
    }


    public void gameReset() {
        maze.resetMaze();
        step = 0;
        runStatus = 0;
    }


    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        g2.drawString("Robot: " + robot.echoName(), 30, 40);
        g2.drawString("Step: " + step, 30, 60);
        for (MazeCell c : maze.getMaze()) {
            g2.setColor(c.getColor());
            g2.fill(c.getShape());

        }
        if (runStatus == -1) {
            g2.drawString("Game status: You are cheating!!", 30, 80);
        } else if (runStatus == 2) {
            g2.drawString("Game status: The ui.robot has found the exit!!", 30, 80);
        } else {
            g2.drawString("Game status: ", 30, 80);
        }
    }


    private boolean validPace() {
        int Xmove = Math.abs(lastPosition.getX() - robot.getLocation().getX());
        int Ymove = Math.abs(lastPosition.getY() - robot.getLocation().getY());
        return (Xmove > 2 || Ymove > 2) ? false : true;
    }


    private boolean notThroughWall() {
        int Xcell = (lastPosition.getX() + robot.getLocation().getX()) / 2;
        int Ycell = (lastPosition.getY() + robot.getLocation().getY()) / 2;
        MazeCell c = maze.getMaze().get(Xcell + Ycell * maze.WIDTH);
        return c.ifWay;
    }

}
