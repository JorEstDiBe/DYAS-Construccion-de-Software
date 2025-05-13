/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balitechy.spacewar.main.factory;

import com.balitechy.spacewar.main.logic.control.BulletController;
import com.balitechy.spacewar.main.logic.entities.Obstacle;
import com.balitechy.spacewar.main.logic.entities.Player;
import com.balitechy.spacewar.main.logic.entities.VectorObstacle;
import com.balitechy.spacewar.main.logic.entities.VectorPlayer;
import com.balitechy.spacewar.main.rendering.BackgroundRenderer;
import com.balitechy.spacewar.main.core.Game;

/**
 *
 * @author Laura
 */
public class VectorGameFactory implements GameFactory {

    @Override
    public Player createPlayer(int x, int y, Game game) {
        return new VectorPlayer(x, y, game); // Usamos la versión vectorial del jugador
    }

    @Override
    public BulletController createBulletController() {
        return new BulletController();
    }

    @Override
    public BackgroundRenderer createBackgroundRenderer() {
        return new BackgroundRenderer(true); // true para vector
    }
    
    @Override
    public Obstacle createObstacle(int x, int y, Game game) {
        return new VectorObstacle(x, y);
    }
}


