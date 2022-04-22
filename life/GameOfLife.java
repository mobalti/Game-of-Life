package life;


import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.*;

public class GameOfLife extends JFrame {

    static final int SCREEN_WIDTH = 400;
    static final int SCREEN_HEIGHT = 400;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static boolean running;
    Universe universe;
    char[][] grid;
    JPanel infoPanel;
    JPanel gamePanel;
    JLabel generationLabel;
    JLabel gLiveLabel;
    int generationCount = 1;
    JToggleButton playToggleButton;
    JButton resetButton;
    Thread thread1;
    boolean isPaused;
    boolean isReset;
    Thread mainThread;

    public GameOfLife() {
        this.setTitle("Game of life");
        universe = new Universe();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);

        this.setSize(700, 500);
        this.setLayout(new BorderLayout());
        running = true;
        universe.setup();
        grid = universe.getGrid();
        infoPanel = new JPanel(new FlowLayout());
        this.mainThread = Thread.currentThread();
        isPaused = true;


        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        String str = String.format("Generation #%d", generationCount);
        generationLabel.setText(str);


        gLiveLabel = new JLabel();
        gLiveLabel.setName("AliveLabel");
        String str2 = String.format("Alive: %d", universe.getAlive());
        gLiveLabel.setText(str2);


        playToggleButton = new JToggleButton();
        playToggleButton.setPreferredSize(new Dimension(70, 24));
        playToggleButton.setFocusPainted(true);
        playToggleButton.setText("Start");
        playToggleButton.setName("PlayToggleButton");
        playToggleButton.addActionListener(e -> isPaused());


        resetButton = new JButton();
        resetButton.setPreferredSize(new Dimension(70, 24));
        resetButton.setText("reset");
        resetButton.setName("ResetButton");
        resetButton.addActionListener(e -> reset());

        infoPanel.add(playToggleButton);
        infoPanel.add(resetButton);


        infoPanel.add(generationLabel);
        infoPanel.add(gLiveLabel);
        infoPanel.setPreferredSize(new Dimension(200, 400));


        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    draw(g);
                } catch (InterruptedException exception) {
                    System.out.println("hello");
                }


            }
        };

        gamePanel.setPreferredSize(new Dimension(450, 450));
        this.add(infoPanel, BorderLayout.WEST);
        this.add(gamePanel, BorderLayout.CENTER);
    }


    public void draw(Graphics g) throws InterruptedException {
        g.setColor(Color.BLACK);
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                int x = i * UNIT_SIZE;
                int y = j * UNIT_SIZE;
                if (grid[i][j] == 'O') {
                    g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);
                    g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);
                }
            }
        }

        if (isPaused) {
            Thread.sleep(1);
        } else {
            getNextGeneration();
        }
        String str3 = String.format("Generation #%d", generationCount);
        generationLabel.setText(str3);
        String str4 = String.format("Alive: %d", universe.getAlive());
        gLiveLabel.setText(str4);
        repaint();
    }

    private void getNextGeneration() throws InterruptedException {
        Thread.sleep(150);
        universe.nextGeneration();
        grid = universe.getGrid();
        generationCount++;
    }

    public void reset() {
        generationCount = 1;
        universe = new Universe();
        universe.setup();
    }

    public void isPaused() {
        if (playToggleButton.isSelected()) {
            isPaused = false;
            System.out.println("is paused");
        } else {
            isPaused = true;
            System.out.println("is not paused");
        }
    }
}

