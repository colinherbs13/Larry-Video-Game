package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import game.scenes.Scene;

// A list of entities. Always use this over ArrayList<Entity> or similar.
public class EntityList implements Iterable<Entity> {
	
	/// Fields
	private ArrayList<Entity> entities;
	private ArrayList<Entity> toAdd;
	private ArrayList<Entity> toRemove;
	private Scene scene;
	private boolean isLocked;
	public boolean isUnsorted;
	
	/// Constructors
	public EntityList(Scene scene) {
		entities = new ArrayList<Entity>();
		toAdd = new ArrayList<Entity>();
		toRemove = new ArrayList<Entity>();
		this.scene = scene;
		isLocked = false;
		isUnsorted = false;
	}
	
	/// Methods
	private void updateLists() {
		
		if (toAdd.size() > 0) {
			for (var entity : toAdd) {
				entities.add(entity);
				entity.added(scene);
			}
			toAdd.clear();
		}
		
		if (toRemove.size() > 0) {
			for (var entity : toRemove) {
				entities.remove(entity);
				entity.removed(scene);
			}
			toRemove.clear();
		}
		
		if (isUnsorted) {
			sort();
			isUnsorted = false;
		}
		
	}

	public void add(Entity entity) {
        if (entity == null) {
            return;
        }
        if (isLocked) {
            if (!entities.contains(entity) && !toAdd.contains(entity))
                toAdd.add(entity);
        } 
        else {
            if (!entities.contains(entity)) {
                entities.add(entity);
                entity.added(scene);
            }
        }

        isUnsorted = true;
    }

    public void remove(Entity entity) {
        if (entity == null) {
            return;
        }
        if (isLocked) {
            if (entities.contains(entity) && !toRemove.contains(entity))
                toRemove.add(entity);
        } 
        else {
            if (entities.contains(entity)) {
                entities.remove(entity);
                entity.removed(scene);
            }
        }
    }
	
	public void update() {
		isLocked = true;
		updateLists();
		
		for (var entity : entities) {
			if (entity.isActive)
				entity.update();
		}
		isLocked = false;
	}
	
	public void clear() {
        entities.clear();
        toAdd.clear();
        toRemove.clear();
    }
	
	public void render() {
		isLocked = true;
		updateLists();
		
		for (var entity : entities) {
			if (entity.isVisible)
				entity.render();
		}
		
		isLocked = false;
	}
	
	public int size() {
		return entities.size();
	}
	
	public void sort() {
		entities.sort(new CompareDepths());
	}
	
	public void markUnsorted() {
		isUnsorted = true;
	}
	
	private class CompareDepths implements Comparator<Entity> {

		@Override
		public int compare(Entity o1, Entity o2) {
			
			return (int)Math.signum((o1.getDepth() - o2.getDepth()));
		}
		
	}
	
	
	@Override
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}
	
}
