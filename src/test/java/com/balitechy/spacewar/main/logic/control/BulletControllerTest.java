/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.balitechy.spacewar.main.logic.control;

import com.balitechy.spacewar.main.core.Game;
import com.balitechy.spacewar.main.factory.SpriteGameFactory;
import com.balitechy.spacewar.main.logic.entities.Bullet;
import com.balitechy.spacewar.main.logic.entities.VectorBullet;
import java.awt.Graphics;
import java.util.LinkedList;
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
public class BulletControllerTest {

    private Game dummyGame() {
        return new Game(new SpriteGameFactory()) { };
    }

    @Test
    public void testAddAndRemoveBullet() {
        BulletController controller = new BulletController();
        Bullet bullet = new Bullet(100, 100, dummyGame());
        controller.addBullet(bullet);
        assertEquals(1, controller.getBullets().size());

        controller.removeBullet(bullet);
        assertEquals(0, controller.getBullets().size());
    }

    @Test
    public void testTickRemovesBulletOffScreen() {
        BulletController controller = new BulletController();

        Bullet bullet = new Bullet(50, -10, dummyGame()) {
            @Override
            public double getY() {
                return -10;
            }
        };

        controller.addBullet(bullet);
        controller.tick();  // debería eliminar la bala por estar fuera de pantalla
        assertEquals(0, controller.getBullets().size());
    }

    @Test
    public void testAddAndRemoveVectorBullet() {
        BulletController controller = new BulletController();
        VectorBullet vb = new VectorBullet(200, 200);
        controller.addBulletV(vb);
        assertEquals(1, controller.getVectorBullets().size());

        controller.removeBulletV(vb);
        assertEquals(0, controller.getVectorBullets().size());
    }

    @Test
    public void testTickRemovesVectorBulletOffScreen() {
        BulletController controller = new BulletController();

        VectorBullet vb = new VectorBullet(100, -5) {
            @Override
            public double getY() {
                return -5;
            }
        };

        controller.addBulletV(vb);
        controller.tick();
        assertEquals(0, controller.getVectorBullets().size());
    }
}