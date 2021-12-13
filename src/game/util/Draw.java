package game.util;

import java.awt.Color;
import java.awt.Graphics2D;
import game.*;
import game.enums.*;
import game.graphics.Texture;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// Handles drawing. All drawing goes through this class's Graphics2D instance.
public class Draw {
	
	/// Fields
	public static Graphics2D g2d;
	public static Texture pixel;
	
	/// Methods
	public static void initialize() {
		//Run this method at the beginning of Game's initialize method.
		pixel = new Texture(1, 1, Color.white);
	}
	
	public static void renderBox(float x, float y, float width, float height, Color color) {
		pixel.render(x, y, width, height, color);
	}
	
	public static void renderHitbox(Entity entity, Color color) {
		pixel.render(entity.position.x - entity.hitbox.getOffset().x, 
				     entity.position.y - entity.hitbox.getOffset().y, 
				     entity.hitbox.getWidth(), 
				     entity.hitbox.getHeight(), 
				     color);
	}
	
	public static void renderHitbox(Entity entity) {
		renderHitbox(entity, Color.red);
	}
}
 