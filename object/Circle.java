package android.example.a2dgame_littleball_androidstudio.object;

import android.content.Context;
import android.example.a2dgame_littleball_androidstudio.Util;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Circle is an abstact class implents draw method from GameObject class to draw objects as a circle
 */

public abstract class Circle extends GameObject {
    private static int counter = 0;
    private static int counterNO = 0;
    protected double radius;
    protected Paint paint;

    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * iscolliding checks if two circle objects are colliding, based on their position and radii
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean iscolliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1,obj2);
//        double distance = Util.getDistance(obj1.getPositionX(),obj2.getPositionX(),obj1.getPositionY(), obj2.getPositionY());

        // check if two circle objects are overlapping
        double distanceToCollide = obj1.getRadius() + obj2.getRadius();
        if (distance < distanceToCollide) {

            return true;
        }

        return false;


    }

    private double getRadius() {
        return radius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }
}
