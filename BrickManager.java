package brickBreaker;

import java.awt.*;

public class BrickManager {

    public int bricks[][];
    public int brickWidth;
    public int brickHeight;

    public BrickManager(int rows, int cols) {
        bricks = new int[rows][cols];
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                bricks[i][j] = 1;
            }
        }
        brickWidth = 540 / cols;
        brickHeight = 150 / rows;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrick(int value, int row, int col) {
        bricks[row][col] = value;
    }
}
