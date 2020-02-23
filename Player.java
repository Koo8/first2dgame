package android.example.a2dgame_littleball_androidstudio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

class Player {
//    private Context context;
    private double positionX;
    private double postionY;
    private double radius;
    private Paint paint;

    public Player(Context context, double positionX, double postionY, double radius) {
//        this.context = context;
        this.positionX = positionX;
        this.postionY = postionY;
        this.radius = radius;
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)postionY, (float)radius, paint);
    }

    public void update() {
    }

    public void setPosition(double x, double y) {
        this.positionX = x;
        this.postionY = y;
    }
}
