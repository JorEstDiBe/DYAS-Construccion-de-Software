/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balitechy.spacewar.main.rendering;

/**
 *
 * @author Laura
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageFlyweightFactory {
    private static final Map<String, BufferedImage> imageCache = new HashMap<>();

    public static BufferedImage getImage(String path, int x, int y, int width, int height) throws IOException {
        String key = path + ":" + x + ":" + y + ":" + width + ":" + height;

        if (!imageCache.containsKey(key)) {
            SpritesImageLoader loader = new SpritesImageLoader(path);
            loader.loadImage();
            BufferedImage image = loader.getImage(x, y, width, height);
            imageCache.put(key, image);
        }

        return imageCache.get(key);
    }
}