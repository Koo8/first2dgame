package android.example.a2dgame_littleball_androidstudio;

import android.content.Context;
import android.content.res.Resources;
import android.example.a2dgame_littleball_androidstudio.object.Circle;
import android.example.a2dgame_littleball_androidstudio.object.Enemy;
import android.example.a2dgame_littleball_androidstudio.object.Player;
import android.example.a2dgame_littleball_androidstudio.object.Spell;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// game is responsible for all objects and updates of states and render to screen
class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final JoyStick joyStick;
    private GameLoop gameloop;
    private Resources resources;

    // create many enemies
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    // create many spells that can help player to battle enemies
    private List<Spell> spellList = new ArrayList<Spell>();
    // counter is used for testing and debugging
    private int counter = 0;

    public Game(Context context) {
        super(context);
        // get surfaceholder and callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameloop = new GameLoop(this, surfaceHolder);

        // initialize player
        this.resources = getResources();
        joyStick = new JoyStick(275, 700, 70, 40);
        player = new Player(getContext(), 200, 500, 30, joyStick);
//        enemy = new Enemy(getContext(),300,100,15,player);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // this is used for handling touchEvent
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // ******there are three scenarios for ACTION_DOWN
                   // first: joystick was pressed before this event,
                if (joyStick.getIsPressed()) { // this means there is a second press down event on screen. This is the trigger for player to cast a spell
                    spellList.add(new Spell(getContext(), player));

                    // second: joystick is pressed down by this event, setISPressed(true), refrain from casting spell
                } else if (joyStick.isPressed(event.getX(), event.getY())){
                    joyStick.setIsPressed(true);
                    // third: joystick was not and is not pressed when the press down action happened, that indicates other part of screen was touched, this will cast a spell
                } else {
                    spellList.add(new Spell(getContext(), player));
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                // this doesn't include a scenario that the joystick was pressed down while another finger move around on screen, this action will create unwanted acturator,  will deal with this later on.
                if (joyStick.getIsPressed()) {
                    joyStick.setActuator(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joyStick.setIsPressed(false);
                joyStick.resetActuator();
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
        joyStick.draw(canvas);
        for (Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }
        for (Spell spell: spellList) {
            spell.draw(canvas);
            Log.d("***********", "draw spell " + counter++);
        }
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameloop.getAverageUPS());
        Paint paint = new Paint();
        int color = getResources().getColor(R.color.magenta);
//        int color = resources.getColor(R.color.magenta, theme);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 50, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameloop.getAverageFPS());
        Paint paint = new Paint();
        int color = getResources().getColor(R.color.magenta);
//        int color = resources.getColor(R.color.magenta,theme);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {
        player.update();
        joyStick.update();
        // add enemy when it is ready to spawn
        if (Enemy.isReadyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }
        // update each enemy
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        // update each spell
        for (Spell spell : spellList) {
            spell.update();
        }

        // remove enemy object that collide with player
        // use Itarator class to loop through the enemyList and check the distance between each enemy and the player
        Iterator<Enemy> enemyIterator = enemyList.iterator();
        while (enemyIterator.hasNext()) {
            if (Circle.iscolliding(enemyIterator.next(), player)) {
                enemyIterator.remove();
            }
        }
    }
}
