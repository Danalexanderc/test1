package com.daniel_carroll.daniel.birdgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.daniel_carroll.daniel.birdgame.screens.LevelType;
import com.daniel_carroll.daniel.birdgame.screens.Splash;
import com.daniel_carroll.daniel.birdgame.screens.Level;
import com.daniel_carroll.daniel.birdgame.utility.Util;

public final class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Paint paint = new Paint();
    private Level currentLevel;
    private LevelType currentLevelType;

    public GameView(Context context)
    {
        super(context);

        paint.setColor(Color.rgb(0,0,0));
        Util.init();

        currentLevelType = LevelType.SPLASH;
        currentLevel = new Splash(this.getContext());

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        System.out.println("\nSurface Changed");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        System.out.println("\nSurface Created");
        currentLevel.surfaceCreated();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        System.out.println("\nSurface Destroyed");
        currentLevel.surfaceDestroyed();

        boolean retry = true;
        while(retry)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            retry=false;
        }
    }

    public void update()
    {
        Util.update();

        currentLevel.updateClock();
        currentLevel.update();

        if(currentLevel.desiredLevel() != currentLevelType)
        {
            currentLevel = currentLevel.exit();
            currentLevelType = currentLevel.desiredLevel();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {

            currentLevel.draw(canvas);

            //Draw black bars to adjust screen ratio (if needed).
            if(Util.getBars() == Util.Bars.SIDES || Util.getBars() == Util.Bars.TOPS) {
                canvas.drawRect(Util.getBar1(), paint);
                canvas.drawRect(Util.getBar2(), paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Util.settX((int)event.getX());
        Util.settY((int)event.getY());

        int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                Util.setPressed(true);
                break;
            case MotionEvent.ACTION_UP:
                Util.setPressed(false);
                break;
        }
        return true;
    }
}
