package android.example.a2dgame_littleball_androidstudio.object;

import android.example.a2dgame_littleball_androidstudio.Util;
import android.graphics.Canvas;

// super class for all world objects
public abstract class GameObject {
    public double positionX;
    public double positionY;

    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void update();// no argument is needed, so Joystick is removed. some subclass locks the movement to other objects such as player
    // therefore, the Joystick that player is depending on for update position is put into the player's constructor
    public abstract void draw(Canvas canvas); // add **abstract** to force all subclass to implement the method with their own procedure

    public void setPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }
    public  double getPositionX() {
        return positionX;
    }

    public  double getPositionY() {
        return positionY;
    }
//  getDistanceBetweenObjects has to use a variable of distance, the Circle class "isColliding" method needs that output of distance.
    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        double distance =Util.getDistance(obj1.getPositionX(), obj2.getPositionX(), obj1.getPositionY(), obj2.getPositionY());
        return  distance;
    }
}
