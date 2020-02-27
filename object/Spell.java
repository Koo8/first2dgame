package android.example.a2dgame_littleball_androidstudio.object;

import android.content.Context;
import android.example.a2dgame_littleball_androidstudio.GameLoop;
import android.example.a2dgame_littleball_androidstudio.R;

import androidx.core.content.ContextCompat;

public class Spell extends Circle{
    private Player spellCaster;
    public static final double SPEED_PIXELS_PER_SECOND = 800;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public Spell(Context context, Player spellCaster) {
        // spell x and y position is determined by player which is named spellCaster
        super(context, ContextCompat.getColor(context, R.color.spell),
                spellCaster.getPositionX(),spellCaster.getPositionY(),20);
        this.spellCaster = spellCaster;

        //define velocityX and velocityY
        velocityX = spellCaster.getDirectionX()*MAX_SPEED;
        velocityY = spellCaster.getDirectionY()*MAX_SPEED;
    }



    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;

    }
}
