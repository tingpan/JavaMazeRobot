package ui.mazeTest;

import ui.robot.*;
import ui.robot.Robot;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MazeFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    Robot[] robots = {new RightHand(), new LeftHand(), new RandomRobot(), new RandomMemory()};
    private JPanel mainPanel = new JPanel();
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");
    private JLabel labelRobot = new JLabel("Choose Robot:");
    private JLabel labelMaze = new JLabel("Choose Maze:");
    private GameManager canvas = new GameManager(new Maze("hak-10x10.txt"), new RightHand());
    private JComboBox<String> robotChoose = new JComboBox<String>(getRobotName());
    private JComboBox<String> mazeChoose = new JComboBox<String>(getMazeName());

    public MazeFrame() {

        setSize(800, 900);
        setLocation(300, 200);
        setResizable(true);
        addComponents(this);

        startButton.addActionListener(e -> {

            Runnable r = new GameRunnable(canvas);
            Thread t = new Thread(r);
            t.start();

        });

        stopButton.addActionListener(e -> {
            canvas.gameReset();
            canvas.repaint();
        });

        mazeChoose.addActionListener(e -> {
            String content = mazeChoose.getSelectedItem().toString();
            if (canvas.runStatus != 1) {
                canvas.setMaze(new Maze(content));
                canvas.gameReset();
                canvas.repaint();
            }
        });

        robotChoose.addActionListener(e -> {
            int index = robotChoose.getSelectedIndex();
            if (canvas.runStatus != 1) {
                canvas.setRobot(robots[index]);
                canvas.gameReset();
                canvas.repaint();
            }
        });

    }


    private void addComponents(Container pane) {
        pane.setLayout(null);
        mainPanel.setBounds(0, 0, 1280, 100);
        mainPanel.setLayout(null);
        labelRobot.setBounds(10, 30, 120, 20);
        labelRobot.setFont(new Font("", Font.PLAIN, 12));
        robotChoose.setBounds(140, 30, 250, 20);

        labelMaze.setBounds(10, 60, 120, 20);
        labelMaze.setFont(new Font("", Font.PLAIN, 12));
        mazeChoose.setBounds(140, 60, 250, 20);

        mainPanel.add(mazeChoose);
        mainPanel.add(labelMaze);
        mainPanel.add(robotChoose);
        mainPanel.add(labelRobot);
        startButton.setBounds(500, 35, 120, 30);
        stopButton.setBounds(640, 35, 120, 30);
        mainPanel.add(stopButton);
        mainPanel.setBorder(BorderFactory.createTitledBorder("Maze Choose"));
        mainPanel.add(startButton);
        canvas.setBounds(0, 100, 900, 1280);
        canvas.setBackground(Color.white);
        canvas.setBorder(BorderFactory.createTitledBorder("Maze Display"));
        pane.add(mainPanel);
        pane.add(canvas);

    }


    private String[] getMazeName() {
        File file = new File("src_maze");
        return file.list();
    }


    private String[] getRobotName() {

        return new String[]{
                "Right Hand Robot",
                "Left Hand Robot",
                "Random Robot",
                "Random Memory Robot",
        };

    }


    class GameRunnable implements Runnable {

        GameManager canvas;

        public GameRunnable(GameManager canvas) {
            this.canvas = canvas;
        }

        public void run() {
            if (canvas.runStatus != 1) {
                canvas.gameStart();
                canvas.repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (canvas.runStatus == 1) {
                    canvas.repaint();
                    canvas.gameRun();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }
}


