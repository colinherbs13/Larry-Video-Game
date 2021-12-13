package game.util;

import java.util.ArrayList;
import java.util.Queue;

import game.Game;

/// WORK IN PROGRESS. Will look into but probably will be removed. Don't use.
public class Timing {

	private ArrayList<Request> requestList;
	
	public Timing() {
		requestList = new ArrayList<Request>();
	}
	
	public void update() {
		for (var r : requestList) {
			if (r.isDone == true) {
				requestList.remove(r);
			} else {
				r.update();
			}
		}
	}
	
	public interface Foo {
		void function(float t);
	}
	
	private class Request {
		float startValue;
		float endValue;
		float timeStart;
		float timeEnd;
		Easing.Easer easingMethod;
		Boolean isDone;
		
		public Request(float startValue, float endValue, float duration, Easing.Easer method) {
			this.startValue = startValue;
			this.endValue = endValue;
			this.timeStart = (float)Game.instance.rawTimeElapsed;
			this.timeEnd = timeStart + duration;
			easingMethod = method;
			isDone = false;
		}
		
		public void update() {
			
		}
		
		
	}
}
