package life;

import java.util.Random;

public class Universe {
    public static final char NO = ' ';
    public static final char YES = 'O';
    private final int size;
    private final Random random;
    private char[][] grid;
    private int alive;

    public char[][] getGrid() {
        return grid;
    }

    public Universe() {
        this.size = 20;
        this.random = new Random();
    }

    void setup() {
        grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    grid[i][j] = YES;

                } else {
                    grid[i][j] = NO;
                }
            }
        }
    }

    void nextGeneration() {
        alive = 0;
        char[][] next = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int neighbors = countNeighbors(grid, i, j);
                var state = grid[i][j];
                if (state == NO && neighbors == 3) {
                    next[i][j] = YES;
                    alive++;
                } else if (state == YES && (neighbors < 2 || neighbors > 3)) {
                    next[i][j] = NO;
                } else {
                    if (state == YES) {
                        alive++;
                    }
                    next[i][j] = state;
                }
            }
        }
        grid = next;
    }

    private int countNeighbors(char[][] grid, int i, int j) {
        int count = 0;
        int cols = size;
        int rows = size;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                var row = (i + k + rows) % rows;
                var col = (j + l + cols) % cols;
                if (grid[row][col] == YES) {
                    count++;
                }
            }
        }

        if (grid[i][j] == YES) {
            count--;
        }

        return count;
    }


    void print() {
        System.out.println("Alive: " + alive);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public int getAlive() {
        alive = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == YES) {
                    alive++;
                }
            }
        }
        return alive;
    }
}

