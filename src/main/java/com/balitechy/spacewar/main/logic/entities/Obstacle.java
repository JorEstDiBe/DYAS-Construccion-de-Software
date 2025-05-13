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

public abstract class Obstacle {
    double x;
    double y;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private int speed;
    
    public Obstacle(double x, double y) {
        this.x = x;
        this.y = y;
        this.speed = (int)(Math.random() * 3) + 1;
    }
    
    public void tick() {
        y += speed;
    }
    
    public abstract void render(Graphics g);
    
    public boolean collidesWith(Player player) {
        return x < player.getX() + Player.WIDTH &&
               x + WIDTH > player.getX() &&
               y < player.getY() + Player.HEIGHT &&
               y + HEIGHT > player.getY();
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
}