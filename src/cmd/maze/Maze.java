package cmd.maze;

import cmd.exceptions.MazeFileException;
import cmd.exceptions.RobotMoveException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Maze {

    public final int WIDTH;
    public final int HEIGHT;
    private final Position EXIT;
    private final Position START = new Position(1, 1);
    //use ArrayList to store cmd.maze
    private ArrayList<MazeCell> maze = new ArrayList<MazeCell>();

    //read cmd.maze from the file,
    //if file dose not exist or an IO error occurs, throw an exception.
    public Maze(String arg) throws MazeFileException {

        int row = 1;
        int column = 1;
        int exitX, exitY, i;

        FileInputStream file;

        try {
            String mazeLocation = "src_maze/";
            file = new FileInputStream(mazeLocation + arg);

        } catch (FileNotFoundException e) {

            //custom error
            throw new MazeFileException("File does not exist");
        }

        try {
            i = file.read();
            while (i != -1) {
                while ((char) i != '\n') {
                    int y = row - 1;
                    int x = column - 1;
                    char smbol = (char) i;

                    //add a mazeCell into the mazeCell ArrayList
                    maze.add(new MazeCell(x, y, smbol));
                    i = file.read();
                }
                row++;
                column = 0;
                i = file.read();
            }

            file.close();
        } catch (IOException e) {
            throw new MazeFileException("Can not read file");
        }

        //get the HEIGHT, WIDTH and EXIT of the cmd.maze.
        HEIGHT = row - 1;

        //If file is empty, throw an error.
        if (HEIGHT == 0) {
            throw new MazeFileException("File is empty");
        }

        WIDTH = maze.size() / (row - 1);

        exitX = WIDTH - 2;
        exitY = HEIGHT - 2;
        EXIT = new Position(exitX, exitY);

    }


    void printMaze() {
        int i = 0;

        for (MazeCell c : maze) {
            c.printCell();
            if (i % WIDTH == WIDTH - 1) {
                System.out.print('\n');
            }
            i++;
        }
    }


    public ArrayList<MazeCell> getMaze() {
        return maze;
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


    public Position getEXIT() {
        return EXIT;
    }


    public Position getSTART() {
        return START;
    }

}
