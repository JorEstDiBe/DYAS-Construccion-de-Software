package com.balitechy.spacewar.main.logic.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.balitechy.spacewar.core.Game;

public class PlayerTest {

    @Test
    void playerShouldMoveLeft() {
        // Se crea un Game simulado (mock) para pasarlo al constructor
        Game mockGame = mock(Game.class);

        // Jugador inicia en posición (100, 100)
        Player player = new Player(100, 100, mockGame);

        // Movimiento hacia la izquierda
        player.moveLeft();

        // Comprobamos que la posición X disminuyó
        assertTrue(player.getX() < 100);
    }
}
