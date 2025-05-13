/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.balitechy.spacewar.main.logic.entities;

import com.balitechy.spacewar.main.core.Game;
import com.balitechy.spacewar.main.factory.SpriteGameFactory;
import java.awt.Graphics;
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
public class BulletTest {

    // Subclase anónima de Game para pruebas
    private Game dummyGame() {
        return new Game(new SpriteGameFactory()) { };
    }

    @Test
    public void testBulletMovesUp() {
        Bullet bullet = new Bullet(100, 100, dummyGame());
        double initialY = bullet.getY();
        bullet.tick(); // se supone que baja Y
        assertTrue(bullet.getY() < initialY, "La bala no se movió hacia arriba.");
    }

    @Test
    public void testInitialPositionIsCorrect() {
        Bullet bullet = new Bullet(50, 80, dummyGame());
        assertEquals(50, bullet.getX());
        assertEquals(80, bullet.getY());
    }
}