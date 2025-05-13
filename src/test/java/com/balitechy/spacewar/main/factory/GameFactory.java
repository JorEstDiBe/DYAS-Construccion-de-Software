/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.balitechy.spacewar.main.factory;

import com.balitechy.spacewar.main.logic.control.BulletController;
import com.balitechy.spacewar.main.logic.entities.Obstacle;
import com.balitechy.spacewar.main.logic.entities.Player;
import com.balitechy.spacewar.main.rendering.BackgroundRenderer;
import com.balitechy.spacewar.main.core.Game;

/**
 *
 * @author Laura
 */
public interface GameFactory {
    
    Player createPlayer(int x, int y, Game game);
    BulletController createBulletController();
    BackgroundRenderer createBackgroundRenderer();
    Obstacle createObstacle(int x, int y, Game game);
    
}
