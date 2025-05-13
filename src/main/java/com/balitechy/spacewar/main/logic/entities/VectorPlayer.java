/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.balitechy.spacewar.main.logic.entities;

/**
 *
 * @author Laura
 */

import com.balitechy.spacewar.main.logic.entities.Player;
import com.balitechy.spacewar.main.core.Game;
import java.awt.Graphics;
import java.awt.Polygon;

public class VectorPlayer extends Player {
    private static final int SIZE = 56; 
    private static final int HEIGHT = 28; 

    public VectorPlayer(int x, int y, Game game) {
        super(x, y, game);
    }

    @Override
    public void render(Graphics g) {
        
        double[] xPoints = { x, x + SIZE / 2.0, x - SIZE / 2.0 };
        double[] yPoints = { y, y - HEIGHT, y - HEIGHT }; 

        //Castear valores
        int[] intX = new int[xPoints.length];
        int[] intY = new int[yPoints.length];

        for (int i = 0; i < xPoints.length; i++) {
            intX[i] = (int) Math.round(xPoints[i]);
            intY[i] = (int) Math.round(yPoints[i]);
        }

        g.fillPolygon(intX, intY, 3);
    }
}


