package ui.mazeTest;

import ui.exceptions.RobotMoveException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Maze {

    public final Position EXIT;
    public final Position START = new Position(1, 1);
    public final int WIDTH;
    final String mazeFile;
    final int HEIGHT;
    private ArrayList<MazeCell> maze = new ArrayList<MazeCell>();

    public Maze(String arg) {
        mazeFile = arg;
        int row = 1;
        int column = 1;
        int exitX, exitY;

        try {
            FileInputStream file = new FileInputStream("src_maze/" + mazeFile);
            int i = file.read();

            while (i != -1) {
                while ((char) i != '\n') {
                    int y = row - 1;
                    int x = column - 1;
                    char smbol = (char) i;
                    maze.add(new MazeCell(x, y, smbol));
                    i = file.read();
                    column++;
                }
                row++;
                column = 1;
                i = file.read();
            }

            file.close();

        } catch (IOException e) {
            System.out.print("Error:Couldn't find the file.");
            System.exit(-1);
        }

        HEIGHT = row - 1;
        WIDTH = maze.size() / (row - 1);

        exitX = WIDTH - 2;
        exitY = HEIGHT - 2;
        EXIT = new Position(exitX, exitY);
    }


    public ArrayList<MazeCell> getMaze() {
        return maze;
    }

    public void resetMaze() {
        for (MazeCell c : maze) {
            c.setFootprint(false);
            c.setIfVisit(false);
            c.setMark(true);
        }
    }

    public MazeCell getCell(Position p) throws RobotMoveException {
        int index;
        index = p.getX() + p.getY() * WIDTH;
        try {
            return maze.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RobotMoveException("Robot out of bounds");

        }
    }
}
