package android.example.a2dgame_littleball_androidstudio;

public class Util {
    public static double getDistance(double pointA_x, double pointB_x, double pointA_y, double pointB_y) {
        return Math.sqrt(
                Math.pow((pointA_x -pointB_x),2) +
                        Math.pow((pointA_y - pointB_y), 2)
        );
    }
}
