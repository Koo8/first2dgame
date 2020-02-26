package android.example.a2dgame_littleball_androidstudio.object;

import android.content.Context;
import android.example.a2dgame_littleball_androidstudio.GameLoop;
import android.example.a2dgame_littleball_androidstudio.JoyStick;
import android.example.a2dgame_littleball_androidstudio.R;
import android.example.a2dgame_littleball_androidstudio.object.Circle;
import android.util.Log;

import androidx.core.content.ContextCompat;

/**
 * player is the main character in the game, a touch joystick can control its move,
 * Player class is a subclass of Circle class, which is a subclass of GameObject
 */
public class Player extends Circle {
    private final JoyStick joyStick;

    public static final double SPEED_PIXELS_PER_SECOND = 400;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;


    public Player(Context context, double positionX, double positionY, double radius, JoyStick joyStick) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joyStick = joyStick;
    }

    public void update() {

        // update velocity based on actuator of joystick
        velocityX = joyStick.getactuatorX()* MAX_SPEED;
        velocityY = joyStick.getactuatorY() * MAX_SPEED;
        // update position
        positionX += velocityX;
        positionY += velocityY;
    }
}
