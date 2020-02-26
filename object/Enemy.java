package android.example.a2dgame_littleball_androidstudio.object;

import android.content.Context;
import android.example.a2dgame_littleball_androidstudio.GameLoop;
import android.example.a2dgame_littleball_androidstudio.R;
import android.util.Log;
import androidx.core.content.ContextCompat;

/**
 * Enemy is a character that always goes in the direction of Player, chase player, it is a subclass of Circle,
 * which is a subclass of GameObject
 */
public class Enemy extends Circle {
    private static final int NUM_OF_SPAWNS_PER_MINUTE =20;
    private static final double NUM_OF_SPAWNS_PER_SECOND = NUM_OF_SPAWNS_PER_MINUTE / 60.0; // this 60.0 has to be this way, otherwise using 60 will make this constant into 0, and therefore create "INFINITE' denomenator;
    private static final double TIME_SPAN_BETWEEN_SPAWNS = GameLoop.MAX_UPS/ NUM_OF_SPAWNS_PER_SECOND;
    private static double timeLeftBeforeSpawn = TIME_SPAN_BETWEEN_SPAWNS ;
    private static int counter = 0;
    private Player player;
    public static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND * 0.6;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Enemy(Context context, double positionX, double positionY, double radius, Player player) {
        super(context, ContextCompat.getColor(context, R.color.enemy),positionX,positionY,radius);
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(context, ContextCompat.getColor(context, R.color.enemy),
                Math.random()*1000,Math.random()*1000,20);
        this.player = player;
    }


    // update velocity of enemy so that the velocity is in the direction of player
    @Override
    public void update() {
        // calculate vector from enemy to player in X and Y
        double distanceToPlayer_X = player.getPositionX() - positionX;
        double distanceToPlayer_y = player.getPositionY() - positionY;

        // calculate absolute distance from enemy(this) to player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        // set verlocity in the direction of player
        double directionX = distanceToPlayer_X/distanceToPlayer;
        double directionY = distanceToPlayer_y/distanceToPlayer;

        // update the position of the enemy
        if (distanceToPlayer > 0 ) { // avoid distance by zero
            velocityX = directionX *MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        } else {
            velocityY = 0.0;
            velocityX = 0.0;
        }
        // update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;
    }

    /**
     * isReadyToSpawn checks if a new enemy is ready to spawn according to the number of spawns within a minute.
     * @return
     */
    public static boolean isReadyToSpawn() {
        if ( timeLeftBeforeSpawn <= 0) {
            Log.d("Enemy &&&&&&& ", "isReadyToSpawn: " + counter++);
            timeLeftBeforeSpawn += TIME_SPAN_BETWEEN_SPAWNS;
            return true;
        } else {
            timeLeftBeforeSpawn --;
            return false;
        }
    }
}
