/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balitechy.spacewar.main.factory;

import com.balitechy.spacewar.main.logic.control.BulletController;
import com.balitechy.spacewar.main.logic.entities.Obstacle;
import com.balitechy.spacewar.main.logic.entities.Player;
import com.balitechy.spacewar.main.logic.entities.SpriteObstacle;
import com.balitechy.spacewar.main.factory.GameFactory;
import com.balitechy.spacewar.main.rendering.BackgroundRenderer;
import com.balitechy.spacewar.main.core.Game;

/**
 *
 * @author Laura
 */
public class SpriteGameFactory implements GameFactory {

    @Override
    public Player createPlayer(int x, int y, Game game) {
        return new Player(x, y, game); // Usamos la versión sprite del jugador
    }

    @Override
    public BulletController createBulletController() {
        return new BulletController();
    }

    @Override
    public BackgroundRenderer createBackgroundRenderer() {
        return new BackgroundRenderer(false); // false para sprite
    }
    
    @Override
    public Obstacle createObstacle(int x, int y, Game game) {
        return new SpriteObstacle(x, y, game);
    }
}

