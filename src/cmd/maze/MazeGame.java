package cmd.maze;

import cmd.exceptions.MazeFileException;
import cmd.robot.*;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MazeGame {

    public static void main(String[] args) {
        //new a array of cmd.ui.robot for the later use.
        Robot[] robots = {new RightHand(), new LeftHand(), new RandomRobot(), new RandomMemory()};
        String fileName = null;

        try {
            fileName = chooseMaze();
        } catch (MazeFileException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

        Maze maze = null;

        try {
            maze = new Maze(fileName);
        } catch (MazeFileException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

        Robot robot = robots[chooseRobot()];
        GameManager gameManager = new GameManager(maze, robot);
        gameManager.gameStart();

    }


    private static String chooseMaze() throws MazeFileException {

        Scanner s = new Scanner(System.in);
        int choose = -1;
        File file = null;

        // get file names from the directory
        try {
            file = new File("src_maze");
        } catch (NullPointerException e) {
            throw new MazeFileException("Directory not found.");
        }
        String[] fileName = file.list();

        //if there is no file
        if (fileName.length == 0) {
            throw new MazeFileException("No file in this Directory");
        }


        print("Please choose a cmd.maze");
        print("---------------------");

        //print filenames
        for (int i = 0; i < fileName.length; i++) {
            print(i + ": " + fileName[i]);
        }

        print("Your choose:");
        while (choose == -1) {
            try {
                choose = s.nextInt();
            } catch (InputMismatchException e) {
                //if input is invalid, loop
                print("Invalid input");
                s.nextLine();
                continue;
            }
            if (choose < 0 || choose > fileName.length - 1) {
                choose = -1;
                print("Invalid input");
            }
        }

        print("---------------------");
        return fileName[choose];


    }


    private static int chooseRobot() {
        Scanner s = new Scanner(System.in);
        int choose = -1;

        String[] robotName = {
                "Right Hand Robot",
                "Left Hand Robot",
                "Random Robot",
                "Random Memory Robot",
        };


        print("Please choose a Robot");
        print("---------------------");

        for (int i = 0; i < robotName.length; i++) {
            print(i + ": " + robotName[i]);
        }

        print("Your choose:");
        while (choose == -1) {
            try {
                choose = s.nextInt();
            } catch (InputMismatchException e) {
                //if input is invalid, loop
                print("Invalid input");
                s.nextLine();
                continue;
            }
            if (choose < 0 || choose > robotName.length - 1) {
                choose = -1;
                print("Invalid input");
            }
        }

        print("---------------------");
        return choose;


    }


    private static void print(String s) {
        System.out.println(s);
    }
}
