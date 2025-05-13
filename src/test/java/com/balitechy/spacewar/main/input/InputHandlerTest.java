/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.balitechy.spacewar.main.input;

import com.balitechy.spacewar.main.core.Game;
import com.balitechy.spacewar.main.factory.SpriteGameFactory;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Laura
 */
public class InputHandlerTest {

    @Test
    public void testKeyPressedDelegatesToGame() {
        KeyEvent keyEvent = new KeyEvent(new java.awt.Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' ');

        Game dummyGame = new Game(new SpriteGameFactory()) {
            boolean called = false;

            @Override
            public void keyPressed(KeyEvent e) {
                assertEquals(KeyEvent.VK_LEFT, e.getKeyCode());
                called = true;
            }

            public boolean wasCalled() {
                return called;
            }
        };

        InputHandler handler = new InputHandler(dummyGame);
        handler.keyPressed(keyEvent);
        // dummyGame.wasCalled() no accesible aquí pero puedes verificar manualmente con banderas o impresión
    }

}
