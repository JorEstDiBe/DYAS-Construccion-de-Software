package com.balitechy.spacewar.main.logic.entities;

import com.balitechy.spacewar.main.core.Game;
import com.balitechy.spacewar.main.rendering.ImageFlyweightFactory;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    double x;
    double y;

    private double velX;
    private double velY;

    public static final int WIDTH = 56;
    public static final int HEIGHT = 28;

    private BufferedImage image;
    private Game game;

    public Player(double x, double y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;

        // Get image for Player		
        try {
            image = ImageFlyweightFactory.getImage("/sprites.png", 219, 304, WIDTH, HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void shoot() {
        if (this instanceof VectorPlayer) {
            // Si es un VectorPlayer, dispara una bala vectorial
            VectorBullet vectorBullet = new VectorBullet(this.x, this.y);
            game.getBullets().addBulletV(vectorBullet);
        } else {
            // Si no es un VectorPlayer, dispara una bala normal
            Bullet bullet = new Bullet(this.x, this.y, game);
            game.getBullets().addBullet(bullet);
        }
    }

    public void tick() {
        x += velX;
        y += velY;

        // To avoid player go outside the arena.		
        if (x <= 0) {
            x = 0;
        }
        if (x >= (Game.WIDTH * Game.SCALE) - WIDTH) {
            x = (Game.WIDTH * Game.SCALE) - WIDTH;
        }
        if (y <= 0) {
            y = 0;
        }
        if (y >= (Game.HEIGHT * Game.SCALE) - HEIGHT) {
            y = (Game.HEIGHT * Game.SCALE) - HEIGHT;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawImage(image, (int) x, (int) y, null);
    }
}
