package entities.util;

public class Utils {
    
    public static float random(double min, double max) {
        return (float) (Math.random() * (max - min) + max);
    }
    
    public static float random(double max) {
        return (float) (Math.random() * max);
    }
    
    public static int floor(float x) {
        return (int) Math.floor(x);
    }
    
    public static float abs(float x) {
        return (float) Math.abs(x);
    }
    
    public static float sq(float x) {
        return (float) Math.pow(x, 2);
    }
    
    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(sq(x1 - x2) + sq(y1 - y2));
    }
}
