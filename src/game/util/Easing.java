package game.util;

import java.lang.Math;

// Stores the Easer interface and easing prefabs.
public class Easing {
	
	/// Interfaces
	public interface Easer {
		float function(float t);
	}
	
	/// Static prefabs
	public static Easer linear = t -> t;
	
	public static Easer quadIn = t -> t * t;
	public static Easer quadOut = inverse(quadIn);
	public static Easer quadInOut = combine(quadIn, quadOut);
	
	public static Easer sineIn = t -> (float)Math.cos((Math.PI/2) * t);
	public static Easer sineOut = t -> (float)Math.sin((Math.PI/2) * t);
	public static Easer sineInOut = t -> (float)(Math.cos(Math.PI * t) - 1) / 2f;
	
    private static final float C1 = 1.70158f;
    private static final float C2 = C1 + 1f;
    private static final float C3 = 7.5625f;
    private static final float C4 = 2.75f;
	
	public static Easer backIn = t -> C2 * t * t * t - C1 * t * t;
	public static Easer backOut = t -> 1 + C2 * (float)Math.pow(t - 1, 3) + C1 * (float)Math.pow(t - 1, 2);
	public static Easer backInOut = combine(backIn, backOut);
	
	public static Easer BounceOut = t ->
    {
        if (t < 1 / C4)
        {
            return C3 * t * t;
        }
        else if (t < 2 / C4)
        {
            return C3 * (t -= 1.5f / C4) * t + 0.75f;
        }
        else if (t < 2.5 / C4)
        {
            return C3 * (t -= 2.25f / C4) * t + 0.9375f;
        }
        else
        {
            return C3 * (t -= 2.625f / C4) * t + 0.984375f;
        }
    };
	
    /// Static methods
	public static Easer inverse(Easer function) {
		// Inverts the given easing function.
		
		return (float t) -> { return 1 - function.function(1 - t); };
	}
	
	public static Easer combine(Easer f1, Easer f2) {
		// Combines two easing functions.
		
		return (float t) -> {
			if (t <= 0.5f) {
				return f1.function(2 * t) / 2;
			} 
			else {
				return f2.function(2 * t - 1) / 2 + 0.5f;
			}
		};
	}
	
	public static float calculate(float t, Easer function) {
		// Returns the value of the easing function.
		
		return function.function(t);
	}
	
	public static float calculate(float start, float end, float elapsedTime, float duration, Easer function) {
		// 1 dimensional easing calculation.
		
		if (elapsedTime >= duration)
        {
            return end;
        }

        float t = elapsedTime / duration;
        float x = function.function(t);
        return (end - start) * x + start;
	}
	
	public static Vector2 calculate(Vector2 start, Vector2 end, float elapsedTime, float duration, Easer function) {
		// 2 dimensional easing calculation.
		
		if (elapsedTime >= duration)
        {
            return end;
        }

        float t = elapsedTime / duration;
        float x = function.function(t);
        return new Vector2((end.x - start.x) * x + start.x, (end.y - start.y) * x + start.y);
	}
	

}
