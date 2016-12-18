package ui.mazeTest;

import javax.swing.*;
import java.awt.*;

public class MazeLaunch {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame mazeFrame = new MazeFrame();
            mazeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mazeFrame.setVisible(true);
        });
    }
}