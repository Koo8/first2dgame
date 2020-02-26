package android.example.a2dgame_littleball_androidstudio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class JoyStick {
    private int outerCircleCenterX;
    private int outerCircleCenterY;
    private int innerCircleCenterX;
    private int innerCircleCenterY;
    private final Paint outerCirclePaint;
    private final Paint innerCirclePaint;
    private final int outerCircleRadius;
    private final int innerCircleRadius;
    private boolean joyStickIsPress = false;
    private double centerToTouchPositionDistance;
    private double actuatorX;
    private double acturtorY;


    public JoyStick(int centerX, int centerY, int outerCircleRadius, int innerCircleRadius) {

        // outer and inner circle make up joystick
        outerCircleCenterX = centerX;
        outerCircleCenterY = centerY;
        innerCircleCenterX = centerX;
        innerCircleCenterY = centerY;

        // paint for outer and inner circle
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // radii of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

    }

    public void draw(Canvas canvas) {
        // draw outer circle
        canvas.drawCircle(outerCircleCenterX, outerCircleCenterY, outerCircleRadius, outerCirclePaint);
        // draw inner circle
        canvas.drawCircle(innerCircleCenterX, innerCircleCenterY, innerCircleRadius, innerCirclePaint);
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterX = (int) (outerCircleCenterX + actuatorX * outerCircleRadius);
        innerCircleCenterY = (int) (outerCircleCenterY + acturtorY * outerCircleRadius);
    }

    public void setIsPressed(boolean b) {
        joyStickIsPress = b;
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        centerToTouchPositionDistance = Util.getDistance(outerCircleCenterX, touchPositionX, outerCircleCenterY, touchPositionY);
        return centerToTouchPositionDistance < outerCircleRadius;
    }

    public boolean getIsPressed() {
        return joyStickIsPress;
    }

    public void setActuator(float x, float y) {
        double deltaX = x - outerCircleCenterX;
        double deltaY = y - outerCircleCenterY;
        double deltaDistance = Util.getDistance(outerCircleCenterX, x, outerCircleCenterY, y);
        if (deltaDistance < outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius;
            acturtorY = deltaY / outerCircleRadius;
        } else {
            actuatorX = deltaX / deltaDistance;
            acturtorY = deltaY / deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        acturtorY = 0.0;
    }

    public double getactuatorX() {
        return actuatorX;
    }

    public double getactuatorY() {
        return acturtorY;
    }
}