package game.scenes;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import game.Entity;
import game.EntityList;
import game.Game;
import game.Renderer;
import game.util.Draw;

// This is the base scene that all child scenes inherit from.
public class Scene {
	
	/// Fields
	public EntityList entities;
	protected HashMap<Integer, Double> layerDepths;
	public Renderer renderer;
	protected Color bgColor;
	protected Boolean hasBegun;
	
	/// Constructors
	public Scene() {
		entities = new EntityList(this);
		layerDepths = new HashMap<Integer, Double>();
		renderer = new Renderer(this);
		bgColor = new Color(0, 0, 0);
		hasBegun = false;
	}
	
	/// Methods
	public void begin() {
		Game.instance.bFrame.setBGColor(bgColor);
	}
	

	public void end() {
		for (var e : entities) {
			for ( var c : e.getComponents()) {
				c = null;
			}
			e = null;
		}
        entities.clear();
        
        System.gc();
        
    }
	
	public void reset() {
		hasBegun = false;
	}
	
	public void update() {
		renderer.update();
		entities.update();
	}
	
	public void render() {
		//Draw.pixel.render(0, 0, Game.instance.getWidth(), Game.instance.getHeight(), Color.black);
		entities.render();
	}
	
	public void add(Entity entity) {
		entities.add(entity);
		setDepth(entity);
	}
	
	public void remove(Entity entity) {
		entities.remove(entity);
	}
	
	public void setDepth(Entity entity) {
        if (entity != null) {
            entity.setDepth(findDepth(entity.getLayer()));
            entities.markUnsorted();
        }
    }
	
	protected double findDepth(int layer) {
		
		final double theta = 0.00001;
		
		double add = 0;
		
		if (layerDepths.containsKey(layer)) {
			add = layerDepths.get(layer);
			layerDepths.put(layer, layerDepths.get(layer) + theta);
		} else {
			layerDepths.put(layer, theta);
		}
		
		return layer + add;
	}
	
	public int numOfEntities() {
		return entities.size();
	}
	
	public int numOfComponents() {
		int sum = 0;
		
		for (var i : entities) {
			sum += i.getComponents().size();
		}
		
		return sum;
	}
	
	
	

}
