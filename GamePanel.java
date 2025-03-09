package brickBreaker;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private boolean isPlaying = false;
    private int currentScore = 0;
    private int bricksRemaining = 21;
    private Timer gameTimer;
    private int speed = 8;
    private int paddleX = 310;
    private int ballX = 120;
    private int ballY = 350;
    private int ballXSpeed = -1;
    private int ballYSpeed = -2;
    private BrickManager brickLayout;

    public GamePanel() {
        brickLayout = new BrickManager(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        gameTimer = new Timer(speed, this);
        gameTimer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // Draw bricks
        brickLayout.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Score Display
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Score: ", 420, 30);
        g.drawString("" + currentScore, 520, 30);

        // Paddle
        g.setColor(Color.GREEN);
        g.fillRect(paddleX, 550, 100, 8);

        // Ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, 20, 20);

        // Game won scenario
        if (bricksRemaining <= 0) {
            isPlaying = false;
            ballXSpeed = 0;
            ballYSpeed = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("You Win! ", 260, 300);
            g.drawString("Score: " + currentScore, 250, 340);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 380);
        }

        // Game over scenario
        if (ballY > 570) {
            isPlaying = false;
            ballXSpeed = 0;
            ballYSpeed = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over! ", 220, 300);
            g.drawString("Score: " + currentScore, 250, 340);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 380);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameTimer.start();
        if (isPlaying) {
            if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(paddleX, 550, 100, 8))) {
                ballYSpeed = -ballYSpeed;
            }
            outerLoop:
            for (int i = 0; i < brickLayout.bricks.length; i++) {
                for (int j = 0; j < brickLayout.bricks[0].length; j++) {
                    if (brickLayout.bricks[i][j] > 0) {
                        int brickX = j * brickLayout.brickWidth + 80;
                        int brickY = i * brickLayout.brickHeight + 50;
                        int brickW = brickLayout.brickWidth;
                        int brickH = brickLayout.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickW, brickH);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            brickLayout.setBrick(0, i, j);
                            bricksRemaining--;
                            currentScore += 5;

                            if (ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) {
                                ballXSpeed = -ballXSpeed;
                            } else {
                                ballYSpeed = -ballYSpeed;
                            }
                        }
                    }
                }
            }
            ballX += ballXSpeed;
            ballY += ballYSpeed;
            if (ballX < 0 || ballX > 670) {
                ballXSpeed = -ballXSpeed;
            }
            if (ballY < 0) {
                ballYSpeed = -ballYSpeed;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddleX >= 600) {
                paddleX = 600;
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
                ballX = 120;
                ballY = 350;
                ballXSpeed = -1;
                ballYSpeed = -2;
                paddleX = 310;
                currentScore = 0;
                bricksRemaining = 21;
                brickLayout = new BrickManager(3, 7);
            }
        }
    }

    public void moveRight() {
        isPlaying = true;
        paddleX += 20;
    }

    public void moveLeft() {
        isPlaying = true;
        paddleX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
