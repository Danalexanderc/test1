package com.daniel_carroll.daniel.birdgame.screens;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.daniel_carroll.daniel.birdgame.utility.SoundManager;
import com.daniel_carroll.daniel.birdgame.utility.SpriteManager;

public abstract class Level {

    protected LevelType levelType;
    protected Context context;
    protected SoundManager soundManager = new SoundManager(0);
    protected SpriteManager spriteManager= new SpriteManager(0);
    private long startTime = -1, destroyedAt = -1, deadTime = 0, deltaTime = 0;

    public abstract void update();

    public abstract void draw(Canvas canvas);

    public abstract LevelType desiredLevel();

    public abstract Level exit();

    public void updateClock()
    {
        if(startTime == -1)
            startTime = SystemClock.elapsedRealtime();

        deltaTime = (SystemClock.elapsedRealtime() - startTime) - deadTime;
    }

    public void surfaceDestroyed()
    {
        destroyedAt = SystemClock.elapsedRealtime();

        soundManager.stopAllSounds(false);
    }

    public void surfaceCreated()
    {
        if(destroyedAt != -1)
            deadTime = deadTime + SystemClock.elapsedRealtime() - destroyedAt;

        soundManager.resumeSounds();
    }

    public long getDeltaTime()
    {
        return deltaTime;
    }
}

