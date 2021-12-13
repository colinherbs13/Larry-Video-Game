package game.util;

import java.awt.Color;
import java.io.File;
import java.lang.Math;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import game.Game;

// Pseudo-static storage class for functions & RNG.
public class Calc {
	
	/// Methods
	public static Color Multiply(Color c1, Color c2) {
		float r1 = (c1.getRed() / 255.0f);
		float g1 = (c1.getGreen() / 255.0f);
		float b1 = (c1.getBlue() / 255.0f);
		float a1 = (c1.getAlpha() / 255.0f);
		
		float r2 = (c2.getRed() / 255.0f);
		float g2 = (c2.getGreen() / 255.0f);
		float b2 = (c2.getBlue() / 255.0f);
		float a2 = (c2.getAlpha() / 255.0f);
		
		return new Color(r1 * r2, g1 * g2, b1 * b2, a1 * a2);
	}
	
	public static float snap(float value, float min, float max) {
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}
	
	public static float min(float value1, float value2) {
		if (value1 < value2)
			return value1;
		return value2;
	}
	
	public static int min(int value1, int value2) {
		if (value1 < value2)
			return value1;
		return value2;
	}
	
	public static Document getXML(String filename) {
		Document doc;
		try {
			File file = new File(Game.contentPath + filename);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			return doc;
		} catch (Exception e) {
			Logger.Log("XML document goofed up somehow. Figure it out.");
		}
		return null;
	}
	
	public static float toFloat(String string) {
		return Float.parseFloat(string);
	}
	
	public static int toInt(String string) {
		return Integer.parseInt(string);
	}
	
	public static Boolean toBoolean(String string) {
		return Boolean.parseBoolean(string);
	}
}
