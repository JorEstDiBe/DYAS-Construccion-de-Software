package com.balitechy.spacewar.main.input;

import com.balitechy.spacewar.main.core.Game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {

    private Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
        
        if (e.getKeyCode() == KeyEvent.VK_R && game.isGameOver()) {
            game.resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        game.keyReleased(e);
    }

}
