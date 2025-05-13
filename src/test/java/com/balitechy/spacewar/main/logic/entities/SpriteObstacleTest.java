/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.balitechy.spacewar.main.logic.entities;

import com.balitechy.spacewar.main.core.Game;
import com.balitechy.spacewar.main.factory.SpriteGameFactory;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Laura
 */
public class SpriteObstacleTest {

    private Game dummyGame() {
        return new Game(new SpriteGameFactory()) {
            @Override
            public com.balitechy.spacewar.main.rendering.SpritesImageLoader getSprites() {
                // Devolvemos una imagen vacía o simulada
                return new com.balitechy.spacewar.main.rendering.SpritesImageLoader("/sprites.png") {
                    @Override
                    public BufferedImage getImage(int x, int y, int width, int height) {
                        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    }
                };
            }
        };
    }

    @Test
    public void testSpriteObstacleMovesDown() {
        SpriteObstacle obstacle = new SpriteObstacle(100, 0, dummyGame());
        double initialY = obstacle.getY();
        obstacle.tick(); // simula movimiento
        assertTrue(obstacle.getY() > initialY, "El SpriteObstacle no se movió hacia abajo.");
    }

    @Test
    public void testInitialPosition() {
        SpriteObstacle obstacle = new SpriteObstacle(42, 17, dummyGame());
        assertEquals(42, obstacle.getX());
        assertEquals(17, obstacle.getY());
    }
}