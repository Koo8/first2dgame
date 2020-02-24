package android.example.a2dgame_littleball_androidstudio;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

class GameLoop extends Thread{
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double averageUPS;
    private double averageFPS;
    static final double MAX_UPS = 60;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private int counter =0;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;

        // start looping when start thread, so extends thread class
        start();
    }
// thread is started by run() loop
    @Override
    public void run() {
        super.run();


           // declare time and cycle count variables for calculating UPS and FPS
        int updateCount = 0;
        int frameCount = 0;
        long startTime, elapsedTime, sleepTime;

        // set startTime b4 the game looping;
        startTime = System.currentTimeMillis();

        // Game loop
        while(isRunning) {
            Canvas canvas = null;
            // ***try to update and render game
                // we need a canvas
            try{
                canvas = surfaceHolder.lockCanvas();
                // by convention, not sure why - to add syncrhonized condition to prevent more thread running at the same time.
                synchronized (surfaceHolder) {
                    game.update();
                    game.draw(canvas);
                    updateCount++;
                }

            }catch (Exception e ) {
                Log.d("***************", "run: " + e.toString());
            } finally {
                if (canvas != null ){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        // update frameCount only when the canvas is exposed to the surfaceholder;
                        frameCount++;
                    }catch (Exception e ) {
                        Log.d("******????", "run in finally block: " + e.toString());
                    }
                }

            }



            // ***pause Game loop to not exceed target UPS

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)(updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            

            // ***skip frames to keep up with target UPS by only update the updateCount , the purpose is to not allow render frame to slow down the update of the game.
                    // adding MAX_UPS condition to enforce the UPS won't go beyond its Max number per second.. this gurantee the UPS will be lose to 60 per scond
            while(sleepTime<0 && updateCount < (MAX_UPS -1)) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            // ***calculate average UPS and FPS
                 // set elapsedTime after the game loop;
            elapsedTime = System.currentTimeMillis() - startTime;
                 // calculate averageUPS and averageFPS when the elapsedTime gets longer than 1 second, reset counter and startTime
                 // this alone will make the render and update not consistent because the elapsedTime gets bigger and counter is keep on changing.
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 *elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }
}
