package com.balitechy.spacewar.main.logic.entities;

import com.balitechy.spacewar.main.core.Game;
import static com.balitechy.spacewar.main.logic.entities.Player.HEIGHT;
import static com.balitechy.spacewar.main.logic.entities.Player.WIDTH;
import com.balitechy.spacewar.main.rendering.ImageFlyweightFactory;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet {

    private double x;
    private double y;
    public static final int WIDTH = 11;
    public static final int HEIGHT = 21;
    private BufferedImage image;

    public Bullet(double x, double y, Game game) {
        this.x = x;
        this.y = y;
        try {
            image = ImageFlyweightFactory.getImage("/sprites.png", 35, 52, WIDTH, HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tick() {
        y -= 5;
    }

    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

}
