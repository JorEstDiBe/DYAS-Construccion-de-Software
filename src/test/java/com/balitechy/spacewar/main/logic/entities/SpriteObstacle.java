/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balitechy.spacewar.main.logic.entities;

import com.balitechy.spacewar.main.logic.entities.Obstacle;
import com.balitechy.spacewar.main.core.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Laura
 */
public class SpriteObstacle extends Obstacle {
    private BufferedImage image;
    
    public SpriteObstacle(int x, int y, Game game) {
        super(x, y);
        this.image = game.getSprites().getImage(100, 100, WIDTH, HEIGHT);
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(image, (int)x, (int)y, null);
    }
}
