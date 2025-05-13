package com.balitechy.spacewar.main.core;

import com.balitechy.spacewar.main.rendering.BackgroundRenderer;
import com.balitechy.spacewar.main.logic.entities.Bullet;
import com.balitechy.spacewar.main.logic.control.BulletController;
import com.balitechy.spacewar.main.factory.GameFactory;
import com.balitechy.spacewar.main.input.InputHandler;
import com.balitechy.spacewar.main.logic.entities.Obstacle;
import com.balitechy.spacewar.main.logic.control.ObstacleController;
import com.balitechy.spacewar.main.logic.entities.Player;
import com.balitechy.spacewar.main.factory.SpriteGameFactory;
import com.balitechy.spacewar.main.rendering.SpritesImageLoader;
import com.balitechy.spacewar.main.logic.entities.VectorBullet;
import com.balitechy.spacewar.main.factory.VectorGameFactory;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public final String TITLE = "Space War 2D";
    private boolean fKeyPressed = false;

    private boolean running = false;
    private Thread thread;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private SpritesImageLoader sprites;
    private GameFactory factory;

    // Game components
    private Player player;
    private BulletController bullets;
    private BackgroundRenderer backgRenderer;
    private boolean isSpriteMode = true;

    private int score = 0;
    private int highScore = 0;
    private boolean gameOver = false;
    private ObstacleController obstacles;
    private int lives = 3;

    public Game(GameFactory factory) {
        this.factory = factory;
    }

    public void init() {
        requestFocus();

        sprites = new SpritesImageLoader("/sprites.png");
        try {
            sprites.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add keyboard listener
        addKeyListener(new InputHandler(this));

        // inicialización de componentes
        player = factory.createPlayer((WIDTH * SCALE - Player.WIDTH) / 2, HEIGHT * SCALE - 50, this);
        bullets = factory.createBulletController();
        obstacles = new ObstacleController(this);

        if (factory instanceof SpriteGameFactory) {
            backgRenderer = new BackgroundRenderer(false); // Modo sprite
        } else if (factory instanceof VectorGameFactory) {
            backgRenderer = new BackgroundRenderer(true); // Modo vector
        }
    }

    public void changeFactory(GameFactory newFactory) {
        this.factory = newFactory;

        init();

        if (factory instanceof SpriteGameFactory) {
            backgRenderer = new BackgroundRenderer(false); // Modo sprite
        } else if (factory instanceof VectorGameFactory) {
            backgRenderer = new BackgroundRenderer(true); // Modo vector
        }
    }

    public SpritesImageLoader getSprites() {
        return sprites;
    }

    public BulletController getBullets() {
        return bullets;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Handle game restart when game is over
        if (gameOver && key == KeyEvent.VK_R) {
            resetGame();
            return;  // Skip other key processing when restarting
        }

        // Skip other key processing if game is over
        if (gameOver) {
            return;
        }

        // Existing key handling for F key (mode switching)
        if (key == KeyEvent.VK_F) {
            if (!fKeyPressed) {
                if (factory instanceof SpriteGameFactory) {
                    System.out.println("Cambiando a modo vector");
                    changeFactory(new VectorGameFactory());
                } else {
                    System.out.println("Cambiando a modo sprite");
                    changeFactory(new SpriteGameFactory());
                }
                fKeyPressed = true;
            }
        }

        // Existing movement controls
        switch (key) {
            case KeyEvent.VK_RIGHT:
                player.setVelX(5);
                break;
            case KeyEvent.VK_LEFT:
                player.setVelX(-5);
                break;
            case KeyEvent.VK_UP:
                player.setVelY(-5);
                break;
            case KeyEvent.VK_DOWN:
                player.setVelY(5);
                break;
            case KeyEvent.VK_SPACE:
                player.shoot();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_F) {
            fKeyPressed = false;
        }

        switch (key) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
                player.setVelX(0);
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                player.setVelY(0);
                break;
        }
    }

    private synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    @Override
    public void run() {
        init();

        long lastTime = System.nanoTime();
        final double numOfTicks = 60.0;
        double ns = 1000000000 / numOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " ticks, fps " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {
        if (!gameOver) {
            player.tick();
            bullets.tick();
            obstacles.tick();

            // Check player collisions with obstacles
            for (Obstacle obstacle : obstacles.getObstacles()) {
                if (obstacle.collidesWith(player)) {
                    lives--;
                    if (lives <= 0) {
                        gameOver = true;
                        if (score > highScore) {
                            highScore = score;
                        }
                    }
                    obstacles.getObstacles().remove(obstacle);
                    break;
                }
            }

            // Check regular bullet collisions
            for (int i = 0; i < bullets.bl.size(); i++) {
                Bullet bullet = bullets.bl.get(i);
                for (int j = 0; j < obstacles.getObstacles().size(); j++) {
                    Obstacle obstacle = obstacles.getObstacles().get(j);
                    if (bullet.getX() < obstacle.getX() + Obstacle.WIDTH
                            && bullet.getX() + Bullet.WIDTH > obstacle.getX()
                            && bullet.getY() < obstacle.getY() + Obstacle.HEIGHT
                            && bullet.getY() + Bullet.HEIGHT > obstacle.getY()) {
                        score += 100;
                        obstacles.getObstacles().remove(obstacle);
                        bullets.bl.remove(bullet);
                        i--;
                        break;
                    }
                }
            }

            // Check vector bullet collisions
            for (int i = 0; i < bullets.vb.size(); i++) {
                VectorBullet bullet = bullets.vb.get(i);
                for (int j = 0; j < obstacles.getObstacles().size(); j++) {
                    Obstacle obstacle = obstacles.getObstacles().get(j);
                    if (bullet.getX() < obstacle.getX() + Obstacle.WIDTH
                            && bullet.getX() + VectorBullet.WIDTH > obstacle.getX()
                            && bullet.getY() < obstacle.getY() + Obstacle.HEIGHT
                            && bullet.getY() + VectorBullet.HEIGHT > obstacle.getY()) {
                        score += 100;
                        obstacles.getObstacles().remove(obstacle);
                        bullets.vb.remove(bullet);
                        i--;
                        break;
                    }
                }
            }

        }
    }

    public GameFactory getFactory() {
        return this.factory;
    }

    private void renderGameOver(Graphics g) {
        // Create semi-transparent overlay
        Color overlay = new Color(0, 0, 0, 180);
        g.setColor(overlay);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Set text color and draw game over messages
        g.setColor(Color.WHITE);

        // Center text calculations
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Draw game over text
        String gameOverText = "GAME OVER";
        int textWidth = g.getFontMetrics().stringWidth(gameOverText);
        g.drawString(gameOverText, centerX - textWidth / 2, centerY - 50);

        // Draw score
        String scoreText = "Score: " + score;
        textWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, centerX - textWidth / 2, centerY - 30);

        // Draw high score
        String highScoreText = "High Score: " + highScore;
        textWidth = g.getFontMetrics().stringWidth(highScoreText);
        g.drawString(highScoreText, centerX - textWidth / 2, centerY - 10);

        // Draw restart instruction
        String restartText = "Press R to Restart";
        textWidth = g.getFontMetrics().stringWidth(restartText);
        g.drawString(restartText, centerX - textWidth / 2, centerY + 10);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame() {
        gameOver = false;
        score = 0;
        lives = 3;
        bullets = factory.createBulletController();
        obstacles = new ObstacleController(this);
        player = factory.createPlayer((WIDTH * SCALE - Player.WIDTH) / 2, HEIGHT * SCALE - 50, this);
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        try {
            backgRenderer.render(g, this);

            if (!gameOver) {
                player.render(g);
                bullets.render(g);
                obstacles.render(g);

                // Draw score and lives
                g.setColor(Color.WHITE);
                g.drawString("Score: " + score, 20, 20);
                g.drawString("Lives: " + lives, 20, 40);
            } else {
                // Game over screen
                renderGameOver(g);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.dispose();
        bs.show();
    }

    private void toggleBackgroundMode() {
        isSpriteMode = !isSpriteMode;
        if (isSpriteMode) {
            backgRenderer = new BackgroundRenderer(false);  // Modo sprite
        } else {
            backgRenderer = new BackgroundRenderer(true);  // Modo vector
        }
    }

    public static void main(String args[]) {
        GameFactory factory = new SpriteGameFactory();
        Game game = new Game(factory);
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }
}
