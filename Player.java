package android.example.a2dgame_littleball_androidstudio;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

class Player {
//    private Context context;
    private double positionX;
    private double postionY;
    private double radius;
    private Paint paint;
    private double velocityX;
    private double velocityY;
    private static double SPEED_PIXELS_PER_SECOND = 400;
    private static double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Player(Resources.Theme theme, Resources resources, double positionX, double postionY, double radius) {
//        this.context = context;
        this.positionX = positionX;
        this.postionY = postionY;
        this.radius = radius;
        paint = new Paint();
        int color = resources.getColor(R.color.player, theme);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)postionY, (float)radius, paint);
    }

    public void update(JoyStick joyStick) {
        velocityX = joyStick.getactuatorX()* MAX_SPEED;
        velocityY = joyStick.getactuatorY() * MAX_SPEED;
        positionX += velocityX;
        postionY += velocityY;
        Log.d("@@@@@@@@@@", "velocityX update method: " + velocityX );
        Log.d("##########", "velocityY update method: " + velocityY );
    }

    public void setPosition(double x, double y) {
        this.positionX = x;
        this.postionY = y;
    }
}
