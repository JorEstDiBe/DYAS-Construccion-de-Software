package com.balitechy.spacewar.main.logic.control;

import com.balitechy.spacewar.main.logic.entities.VectorBullet;
import com.balitechy.spacewar.main.logic.entities.Bullet;
import java.awt.Graphics;
import java.util.LinkedList;

public class BulletController {

    public LinkedList<Bullet> bl = new LinkedList<Bullet>();
    public LinkedList<VectorBullet> vb = new LinkedList<VectorBullet>(); // Balas vectoriales

    public void tick() {
        for (int i = 0; i < bl.size(); i++) {
            if (bl.get(i).getY() < 0) {
                removeBullet(bl.get(i));
            } else {
                bl.get(i).tick();
            }
        }

        for (int i = 0; i < vb.size(); i++) {
            if (vb.get(i).getY() < 0) {
                removeBulletV(vb.get(i)); // Eliminar balas vectoriales fuera de pantalla
            } else {
                vb.get(i).tick(); // Actualiza la posición de la bala vectorial
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < bl.size(); i++) {
            bl.get(i).render(g); // Dibuja las balas con imagen
        }

        for (int i = 0; i < vb.size(); i++) {
            vb.get(i).render(g); // Dibuja las balas vectoriales
        }
    }

    public void addBullet(Bullet bullet) {
        bl.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bl.remove(bullet);
    }

    public void addBulletV(VectorBullet vectorBullet) {
        vb.add(vectorBullet);
    }

    public void removeBulletV(VectorBullet vectorBullet) {
        vb.remove(vectorBullet);
    }

    public LinkedList<Bullet> getBullets() {
        return bl;
    }

    public LinkedList<VectorBullet> getVectorBullets() {
        return vb;
    }
}
