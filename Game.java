package android.example.a2dgame_littleball_androidstudio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.Random;

// game is responsible for all objects and updates of states and render to screen
class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Player player_friend;
    private GameLoop gameloop;
    private double randomX, randomY;
    private Random rand;

    public Game(Context context) {
        super(context);
        // get surfaceholder and callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);




        gameloop = new GameLoop(this, surfaceHolder);

        // initialize player
        player = new Player(getContext(), 2 * 500, 500, 30);
        player_friend = new Player(getContext(), 400, 300, 20);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        rand = new Random();
        randomX = rand.nextDouble() * 100;
        randomY = rand.nextDouble() * 180;

        // this is used for handling touchEvent
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // cursor position
                player.setPosition(event.getX(), event.getY());
                player_friend.setPosition(event.getX() - randomX, event.getY() - randomY);
                return true;
            case MotionEvent.ACTION_MOVE:
                player.setPosition(event.getX(), event.getY());
                player_friend.setPosition(event.getX() - randomX, event.getY() - randomY);
                return true;


        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameloop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        player.draw(canvas);
        player_friend.draw(canvas);
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameloop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 50, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameloop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {
        player.update();
        player_friend.update();
    }
}
