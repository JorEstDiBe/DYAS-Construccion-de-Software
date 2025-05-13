/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balitechy.spacewar.main.logic.entities;

/**
 *
 * @author Laura
 */
import java.awt.Graphics;

public class VectorBullet {

    private double x;
    private double y;
    public static final int WIDTH = 11;
    public static final int HEIGHT = 21;

    public VectorBullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {
        y -= 5;
    }

    public void render(Graphics g) {
        g.setColor(java.awt.Color.BLACK); 
        g.drawLine((int) x, (int) y, (int) x, (int) (y - 10));
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}
