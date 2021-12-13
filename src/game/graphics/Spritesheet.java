package game.graphics;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import game.components.Sprite;
import game.util.Calc;
import game.util.Logger;
import game.util.Vector2;

// Automatically cuts up a spritesheet given an XML file and creates sprites, animations, & textures.
public class Spritesheet {
	
	/// Fields
	private ArrayList<Texture> textures;
	private ArrayList<Animation> animations;
	private ArrayList<Sprite> sprites;
	private Texture parent;
	
	/// Constructors
	private Spritesheet() {
		textures = new ArrayList<Texture>();
		animations = new ArrayList<Animation>();
		sprites = new ArrayList<Sprite>();
	}
	
	public Spritesheet(Texture sheet, String xmlFileName) {
		this();
		this.parent = sheet;
		
		Document xml = Calc.getXML(xmlFileName);
		
		Element root = xml.getDocumentElement();
		
		var spriteList = root.getElementsByTagName("sprite");
		for (int i = 0; i < spriteList.getLength(); i++) {
			var sprite = (Element)spriteList.item(i);
			Sprite tempSprite = new Sprite(null, new Vector2(0,0), sprite.getAttribute("name"));
			var animList = sprite.getElementsByTagName("animation");
			
			for (int j = 0; j < animList.getLength(); j++) {
				var anim = (Element)animList.item(j);
				Animation tempAnim = new Animation(new Vector2(0,0), 
                        Calc.toFloat(anim.getAttribute("delay")),
                        Calc.toBoolean(anim.getAttribute("loops")),
                        anim.getAttribute("name"));
				var textList = anim.getElementsByTagName("texture");
				
				for (int k = 0; k < textList.getLength(); k++) {
					var currTexture = (Element)textList.item(k);
					Texture tempTexture = new Texture(parent, Calc.toInt(currTexture.getAttribute("x")),
						      Calc.toInt(currTexture.getAttribute("y")),
							  Calc.toInt(currTexture.getAttribute("width")),
			                  Calc.toInt(currTexture.getAttribute("height")),
		          new Vector2(Calc.toFloat(currTexture.getAttribute("offsetx")), 
		        		      Calc.toFloat(currTexture.getAttribute("offsety"))));
					
					textures.add(tempTexture);
					tempAnim.addTexture(tempTexture);
				}
				animations.add(tempAnim);
				tempSprite.addAnimation(tempAnim.name, tempAnim);
			}
			sprites.add(tempSprite);
		}
	}
	
	/// Methods
	public Texture getTexture() {
		return parent;
	}
	
	public Sprite getSprite(int index) {
		return sprites.get(index);
	}
	
	public Sprite getSprite(String name) {
		for (var s : sprites) {
			if (s.name == name) {
				return s;
			}
		}
		return null;
	}

}
