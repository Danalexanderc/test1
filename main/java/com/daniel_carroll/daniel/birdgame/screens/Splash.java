package com.daniel_carroll.daniel.birdgame.screens;

import android.content.Context;
import android.graphics.Canvas;

import com.daniel_carroll.daniel.birdgame.R;
import com.daniel_carroll.daniel.birdgame.utility.SoundManager;
import com.daniel_carroll.daniel.birdgame.utility.Util;


public class Splash extends Level {

    private boolean setSounds = false, setSprites = false;


    public Splash(Context c) {
        context = c;

        levelType = LevelType.SPLASH;

        soundManager = new SoundManager(getDeltaTime());

        soundManager.loadSound(context, R.raw.gunshot, "gunshot1");
        soundManager.loadSound(context, R.raw.gunshot, "gunshot2");
        soundManager.loadSound(context, R.raw.gunshot, "gunshot3");
        soundManager.loadSound(context, R.raw.clicking, "clicking");
        soundManager.loadSound(context, R.raw.explosion, "explosion");
        soundManager.loadSound(context, R.raw.hawk, "hawk");

        spriteManager.loadSprite(context, R.drawable.titlecard,"titleCard", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.bullethole1,"bulletHole1", 0.1, 0.1);
        spriteManager.loadSprite(context, R.drawable.bullethole2,"bulletHole2", 0.1, 0.1);
        spriteManager.loadSprite(context, R.drawable.box,"box", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.brokenbox1,"brokenBox1", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.brokenbox2,"brokenBox2", 1.0, 1.0);

        soundManager.playSound("clicking");
        soundManager.playSound("gunshot1", 4.1, 10);
        soundManager.playSound("gunshot2", 4.3, 30);
        soundManager.playSound("gunshot3", 4.5, 50);
        soundManager.playSound("explosion", 4.9);
        soundManager.playSound("hawk", 4.9);

        spriteManager.displayMovingSprite("box", 20, 2000,0,0,0,4,0);
        spriteManager.displayDelayedSprite("box", 25,1.0,4.01);
        spriteManager.displayDelayedSprite("titleCard", 10,4.3);
        spriteManager.displayDelayedSprite("bulletHole1", 30,850,1500, 0.8, 4.2);
        spriteManager.displayDelayedSprite("bulletHole2", 30,1100,1350, 0.6, 4.4);
        spriteManager.displayDelayedSprite("bulletHole1", 30,1200,800, 0.2, 4.8);
        spriteManager.displayMovingSprite("brokenBox1", 26, 0,0,-300,Util.screenHeight,1.2,5);
        spriteManager.displayMovingSprite("brokenBox2", 26, 0,0,Util.screenWidth,Util.screenHeight,0.8,5);
    }

    @Override
    public void update() {
        soundManager.update(getDeltaTime());
        spriteManager.update(getDeltaTime());


        if(Util.isPressed())
            levelType = LevelType.MAINMENU;

    }

    @Override
    public void draw(Canvas canvas) {
        spriteManager.draw(canvas);

    }

    @Override
    public LevelType desiredLevel() {
        return levelType;
    }

    @Override
    public Level exit() {

        soundManager.stopAllSounds(true);
        spriteManager.exit();
        return new MainMenu(context);
    }
}
