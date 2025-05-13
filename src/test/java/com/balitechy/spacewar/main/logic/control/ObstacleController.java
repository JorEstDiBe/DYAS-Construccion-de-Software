package com.balitechy.spacewar.main.logic.control;

import com.balitechy.spacewar.main.logic.entities.Obstacle;
import com.balitechy.spacewar.main.core.Game;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class ObstacleController {
    private LinkedList<Obstacle> obstacles = new LinkedList<>();
    private Random random = new Random();
    private int spawnTimer = 0;
    private int spawnInterval = 120;
    private Game game;
    
    public ObstacleController(Game game) {
        this.game = game;
    }
    
    public void tick() {
        // Spawn new obstacles
        spawnTimer++;
        if(spawnTimer >= spawnInterval) {
            spawnTimer = 0;
            spawnObstacle();
        }
        
        // Update existing obstacles
        for(int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.tick();
            
            // Remove if out of screen
            if(obstacle.getY() > Game.HEIGHT * Game.SCALE) {
                obstacles.remove(obstacle);
                i--; // Important: Adjust index after removal
            }
        }
    }
    
    public void render(Graphics g) {
        for(Obstacle obstacle : obstacles) {
            obstacle.render(g);
        }
    }
    
    private void spawnObstacle() {
        int x = random.nextInt(Game.WIDTH * Game.SCALE - Obstacle.WIDTH);
        obstacles.add(game.getFactory().createObstacle(x, -Obstacle.HEIGHT, game));
    }
    
    public LinkedList<Obstacle> getObstacles() {
        return obstacles;
    }
    
    public void clear() {
        obstacles.clear();
    }
}