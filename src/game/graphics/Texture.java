package game.graphics;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import game.Game;
import game.util.Calc;
import game.util.Draw;
import game.util.Logger;
import game.util.Vector2;

// Wrapper class for the BufferedImage with much more functionality.
public class Texture {
	
	/// Fields
	private BufferedImage texture;
	private Vector2 offset;
	private Rectangle clipRect;
	
	/// Constructors
	private Texture() {
		texture = null;
		offset = new Vector2(0, 0);
		clipRect = new Rectangle(0, 0, 0, 0);
	}
	
	public Texture(int width, int height) {
		this();
		texture = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clipRect.width = texture.getWidth();
		clipRect.height = texture.getHeight();
	}
	
	public Texture(String filename) {
		this();
		setTexture(filename);
	}
	
	public Texture(int width, int height, Color color) {
		this(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				texture.setRGB(i, j, color.getRGB());
			}
		}
	}
	
	public Texture(Texture parent, int x, int y, int width, int height) {
		this();
		this.texture = parent.texture;
		setClipRect(x, y, width, height);
	}
	
	public Texture(Texture parent, int x, int y, int width, int height, Vector2 offset) {
		this();
		this.texture = parent.texture;
		setClipRect(x, y, width, height);
		this.offset = offset;
	}
	
	/// Methods
	public void setTexture(String filename) {
		try {
			texture = ImageIO.read(new File(Game.contentPath + filename));
			clipRect.width = texture.getWidth();
			clipRect.height = texture.getHeight();
		}
		catch(Exception e) {
			Logger.Log("Texture file " + filename + " could not be found.");
			texture = null;
		}
	}

	
	public int getWidth() {
		return clipRect.width;
	}
	
	public int getHeight() {
		return clipRect.height;
	}
	
	public Texture getSubtexture(int x, int y, int width, int height) {
		return new Texture(this, x, y, width, height);
	}
	
	public void setClipRect(int x, int y, int width, int height) {
		clipRect.setBounds(x, y, width, height);
	}
	
	public void setOffset(float x, float y) {
		offset = new Vector2(x, y);
	}
	
	public Vector2 getOffset() {
		return offset;
	}
	
	public void render(float x, float y, float width, float height, Color color) {
		Draw.g2d.drawImage(this.tint(color).texture, (int)(x-offset.x), (int)(y-offset.y), (int)width, (int)height, null, null);
	}
	
	public void render(float x, float y) {
		Draw.g2d.drawImage(texture, (int)(x-offset.x), (int)(y-offset.y), 
				                    (int)(x-offset.x)+clipRect.width, 
				                    (int)(y-offset.y)+clipRect.height, 
				                    clipRect.x, clipRect.y, 
				                    clipRect.x+clipRect.width, clipRect.y+clipRect.height, null);
		
	}
	
	public void render() {
		render(0, 0);
	}
	
	public void render(Vector2 pos) {
		render(pos.x, pos.y);
	}
	
	public void renderBorder(Vector2 pos) {
		render(pos);
		getBorder().render(pos);
	}
	
	public void renderBorder(float x, float y) {
		renderBorder(new Vector2(x, y));
	}
	
	public void renderBorder() {
		renderBorder(new Vector2(0,0));
	}
	
	public Texture tint(Color color) {
		Texture temp = new Texture(this.getWidth(), this.getHeight());
		
		for (int x = clipRect.x; x < temp.getWidth()+clipRect.x; x++) 
			for (int y = clipRect.y; y < temp.getHeight()+clipRect.y; y++) 
				temp.texture.setRGB(x-clipRect.x, y-clipRect.y, Calc.Multiply(new Color(this.texture.getRGB(x, y), true), color).getRGB());
				
		return temp;
	}
	
	public Texture brighten(int value) {
		Texture temp = this.clone();
		
		for (int x = 0; x < temp.getWidth(); x++) {
			for (int y = 0; y < temp.getHeight(); y++) {
				if (temp.getColor(x, y).getAlpha() != 0) {
					temp.setColor(x, y, new Color((int)Calc.snap(temp.getColor(x, y).getRed()+value, 0, 255),
							(int)Calc.snap(temp.getColor(x, y).getGreen()+value, 0, 255),
							(int)Calc.snap(temp.getColor(x, y).getBlue()+value, 0, 255)));
				}
				
			}
		}
		return temp;
	}
	
	
	
	private void setColor(int x, int y, Color color) {
		this.texture.setRGB(x, y, color.getRGB());
	}
	
	private Color getColor(int x, int y) {
		return new Color(this.texture.getRGB(x, y), true);
	}
	
	public Texture clone() {
		Texture temp = new Texture(this.getWidth(), this.getHeight());
		
		for (int x = 0; x < temp.getWidth(); x++) 
			for (int y = 0; y < temp.getHeight(); y++) 
				temp.texture.setRGB(x, y, this.texture.getRGB(x, y));
		
		return temp;
	}
	
	
	
	public Texture getBorder(Color color) {
		Texture temp = new Texture(this.getWidth(), this.getHeight());
		
		for (int x = 0; x < temp.getWidth(); x++) 
			for (int y = 0; y < temp.getHeight(); y++)
				if (this.getColor(x, y).getAlpha() != 0)
					for (int i = -1; i <= 1; i++)
						for (int j = -1; j <= 1; j++)
							if (Math.abs(i+j) == 1)
								if (x+i >= 0 && x+i < this.getWidth() && y+j >= 0 && y+j < this.getHeight()) {
									if (this.getColor(x+i, y+j).getAlpha() == 0)
										temp.setColor(x, y, color);	
								}
								else {
									temp.setColor(x, y, color);
								}	
		return temp;
	}
	
	public Texture getBorder() {
		return getBorder(Color.black);
	}
	
}
