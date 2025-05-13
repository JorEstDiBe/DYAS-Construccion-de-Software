/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balitechy.spacewar.main.logic.entities;

import com.balitechy.spacewar.main.logic.entities.Obstacle;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Laura
 */
public class VectorObstacle extends Obstacle {
    public VectorObstacle(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
    }
}