package com.balitechy.spacewar.main.rendering;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundRenderer {

    private boolean isVectorMode;  // Para almacenar el estado del fondo

    public BackgroundRenderer(boolean isVectorMode) {
        this.isVectorMode = isVectorMode;
    }

    public void render(Graphics g, Canvas c) throws IOException {
        if (isVectorMode) {
            renderV(g, c);  // Renderizar en modo vectorial
        } else {
            renderS(g, c);  // Renderizar en modo sprite
        }
    }

    // Método para renderizar el fondo tipo sprite
    public void renderS(Graphics g, Canvas c) throws IOException {
        BufferedImage background = ImageFlyweightFactory.getImage("/bg.png", 0, 0, 640, 480);
        g.drawImage(background, 0, 0, c.getWidth(), c.getHeight(), c);
    }

    // Método para renderizar el fondo tipo vector
    public void renderV(Graphics g, Canvas c) throws IOException {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
        g.setColor(Color.BLACK);
        g.drawOval(20, 20, 100, 100);
    }
}
