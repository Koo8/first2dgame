package android.example.a2dgame_littleball_androidstudio.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Circle is an abstact class implents draw method from GameObject class to draw objects as a circle
 */

public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;

    public Circle(Context context,int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
    }
}
