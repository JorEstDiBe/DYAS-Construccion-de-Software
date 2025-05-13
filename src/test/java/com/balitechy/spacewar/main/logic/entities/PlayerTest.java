/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.balitechy.spacewar.main.logic.entities;

import java.awt.Graphics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.balitechy.spacewar.main.core.Game;
import com.balitechy.spacewar.main.factory.SpriteGameFactory;
import com.balitechy.spacewar.main.logic.control.BulletController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Laura
 */
public class PlayerTest {

    // Subclase anónima que anula el método necesario para evitar problemas
    private Game dummyGame() {
        return new Game(new SpriteGameFactory()) {
            @Override
            public BulletController getBullets() {
                return new BulletController();
            }
        };
    }

    @Test
    public void testPlayerMovesLeft() {
        Player player = new Player(100, 100, dummyGame());
        player.setVelX(-5);
        player.tick();
        assertTrue(player.getX() < 100);
    }

    @Test
    public void testPlayerMovesRight() {
        Player player = new Player(100, 100, dummyGame());
        player.setVelX(5);
        player.tick();
        assertTrue(player.getX() > 100);
    }

    @Test
    public void testSetAndGetX() {
        Player player = new Player(0, 0, dummyGame());
        player.setX(150);
        assertEquals(150, player.getX());
    }

    @Test
    public void testSetAndGetY() {
        Player player = new Player(0, 0, dummyGame());
        player.setY(75);
        assertEquals(75, player.getY());
    }

    @Test
    public void testSetVelX() {
        Player player = new Player(0, 0, dummyGame());
        player.setVelX(5);
        player.tick();
        assertEquals(5, player.getX());
    }

    @Test
    public void testSetVelY() {
        Player player = new Player(0, 0, dummyGame());
        player.setVelY(3);
        player.tick();
        assertEquals(3, player.getY());
    }

    @Test
    public void testShootAddsBullet() {
        // Creamos un BulletController que podemos inspeccionar
        BulletController testController = new BulletController();

        Game dummyGame = new Game(new SpriteGameFactory()) {
            @Override
            public BulletController getBullets() {
                return testController;
            }
        };

        Player player = new Player(100, 100, dummyGame);

        int sizeBefore = testController.getBullets().size();
        player.shoot();
        int sizeAfter = testController.getBullets().size();

        assertTrue(sizeAfter > sizeBefore, "El disparo no agregó una bala al controlador.");
    }
}
