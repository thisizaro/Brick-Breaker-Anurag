// GamePanel.java

package brickBreaker;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    // Window constants
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 700;

    // Paddle constants
    private static final int PADDLE_WIDTH = 120;
    private static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_Y = 600;
    private static final int PADDLE_MOVE_STEP = 20;

    // Ball constants
    private static final int BALL_SIZE = 20;
    private static final int BALL_START_X = 220;
    private static final int BALL_START_Y = 450;
    private static final int BALL_SPEED_X = -1;
    private static final int BALL_SPEED_Y = -2;

    // Brick Layout
    // for testing purpose... I will set the number of rows and columns to 1 and 2
    // private static final int BRICK_ROWS = 1;
    // private static final int BRICK_COLUMNS = 2;
    private static final int BRICK_ROWS = 6;
    private static final int BRICK_COLUMNS = 10;

    // Game state
    private boolean isPlaying = false;
    private int currentScore = 0;
    private int bricksRemaining = BRICK_ROWS * BRICK_COLUMNS;
    private Timer gameTimer;
    private int speed = 8;

    // Paddle and ball positions
    private int paddleX = WINDOW_WIDTH / 2 - PADDLE_WIDTH / 2;
    private int ballX = BALL_START_X;
    private int ballY = BALL_START_Y;
    private int ballXSpeed = BALL_SPEED_X;
    private int ballYSpeed = BALL_SPEED_Y;

    // Brick manager
    private BrickManager brickLayout;

    public GamePanel() {
        /*
         * Instructions... ---thisizaro
         * Change this annoying constants to variables... Make global variables for the
         * number of rows and columns of the bricks. AND MOST IMPORTANTLY THE WINDOW
         * SIZE!!
         * IT TURNS INTO MESSED UP CODE IF YOU USE CONSTANTS LIKE THIS... YOU NEED TO
         * CHANGE THE VALUE
         * EVERYWHERE LATER...
         * So please just make a variable and use it everywhere...
         * 
         * for the window.
         * the window size is 900x700 for now but it is creating an issue in the
         * actionPerformed function since it has some other value... Fix the issue...
         */
        brickLayout = new BrickManager(BRICK_ROWS, BRICK_COLUMNS);
        bricksRemaining = brickLayout.bricksRemaining();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        gameTimer = new Timer(speed, this);
        gameTimer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Draw bricks
        brickLayout.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 3, WINDOW_HEIGHT);
        g.fillRect(0, 0, WINDOW_WIDTH, 3);
        g.fillRect(WINDOW_WIDTH - 1, 0, 3, WINDOW_HEIGHT);

        // Score Display
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Score: ", 600, 30);
        g.drawString("" + currentScore, 700, 30);

        // To Win Display (Remaining bricks)
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("To Win: " + bricksRemaining, 300, 30);

        // Paddle
        g.setColor(Color.GREEN);
        g.fillRect(paddleX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Game won scenario
        if (bricksRemaining <= 0) {
            isPlaying = false;
            ballXSpeed = 0;
            ballYSpeed = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("You Win! ", 350, 350);
            g.drawString("Score: " + currentScore, 350, 400);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 330, 450);
        }

        // Game over scenario
        if (ballY > WINDOW_HEIGHT - 30) {
            isPlaying = false;
            ballXSpeed = 0;
            ballYSpeed = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over! ", 320, 350);
            g.drawString("Score: " + currentScore, 350, 400);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 310, 450);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameTimer.start();
        if (isPlaying) {
            if (new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE)
                    .intersects(new Rectangle(paddleX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT))) {
                ballYSpeed = -ballYSpeed;
            }
            for (int i = 0; i < brickLayout.bricks.length; i++) {
                for (int j = 0; j < brickLayout.bricks[0].length; j++) {
                    if (brickLayout.bricks[i][j] > 0) {
                        int brickX = j * brickLayout.brickWidth + 80;
                        int brickY = i * brickLayout.brickHeight + 50;
                        int brickW = brickLayout.brickWidth;
                        int brickH = brickLayout.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickW, brickH);
                        Rectangle ballRect = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);

                        if (ballRect.intersects(brickRect)) {
                            if (brickLayout.bricks[i][j] != -1) { // Not an obstacle
                                brickLayout.hitBrick(i, j);
                                if (brickLayout.bricks[i][j] == 0) {
                                    bricksRemaining--;
                                    currentScore += 5;
                                }

                            }

                            if (ballX + BALL_SIZE - 1 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
                                ballXSpeed = -ballXSpeed;
                            } else {
                                ballYSpeed = -ballYSpeed;
                            }
                            ballX += ballXSpeed;
                            ballY += ballYSpeed;

                        }
                    }
                }
            }
            ballX += ballXSpeed;
            ballY += ballYSpeed;

            if (ballX < 0 || ballX > WINDOW_WIDTH - BALL_SIZE) {
                ballXSpeed = -ballXSpeed;
            }
            if (ballY < 0) {
                ballYSpeed = -ballYSpeed;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddleX >= WINDOW_WIDTH - PADDLE_WIDTH - 10) {
                paddleX = WINDOW_WIDTH - PADDLE_WIDTH - 10;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddleX < 10) {
                paddleX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!isPlaying) {
                isPlaying = true;
                ballX = BALL_START_X;
                ballY = BALL_START_Y;
                ballXSpeed = BALL_SPEED_X;
                ballYSpeed = BALL_SPEED_Y;
                paddleX = WINDOW_WIDTH / 2 - PADDLE_WIDTH / 2;
                currentScore = 0;
                brickLayout = new BrickManager(BRICK_ROWS, BRICK_COLUMNS);
                bricksRemaining = brickLayout.bricksRemaining();
            }
        }
    }

    public void moveRight() {
        isPlaying = true;
        paddleX += PADDLE_MOVE_STEP;
    }

    public void moveLeft() {
        isPlaying = true;
        paddleX -= PADDLE_MOVE_STEP;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
