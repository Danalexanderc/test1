package com.daniel_carroll.daniel.birdgame.screens;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.SystemClock;

import com.daniel_carroll.daniel.birdgame.R;
import com.daniel_carroll.daniel.birdgame.utility.Util;


public class MainMenu extends Level {
    private long startTime;
    private int currentMenu = 0, currentDesiredMenu = 0;
    private boolean pressed = false;

    public MainMenu(Context c) {
        context = c;

        startTime = SystemClock.elapsedRealtime();

        levelType = LevelType.MAINMENU;

        spriteManager.loadSprite(context, R.drawable.menuback,"menuback", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.menubacksettings,"menubacksettings", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.menubacksettings2,"menubacksettings2", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.pressedboxsettings,"pressedboxsettings", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.pressedboxplay,"pressedboxplay", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.pressedboxexit,"pressedboxexit", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.pressedboxback,"pressedboxback", 1.0, 1.0);
        spriteManager.loadSprite(context, R.drawable.jeff,"jeff", 300, 300, false);

        spriteManager.displaySprite("menuback", 10);
    }

    @Override
    public void update() {
        long deltaTime = SystemClock.elapsedRealtime() - startTime;
        spriteManager.update(getDeltaTime());

        if(Util.isPressed() && !pressed) {
            pressed = true;


        }
        if(!Util.isPressed() && pressed)
        {
            pressed = false;
/*
            if(currentMenu == 0)
            {
                if(Util.gettX() > 500)
                {

                }

            }
            else
            {

            }


            /*

            if(currentDesiredMenu == 0)
                    currentDesiredMenu = 1;
                else
                    currentDesiredMenu = 0;

             */
        }

        //if(Util.isPressed() && deltaTime > 1000)
        //    levelType = LevelType.BOARD;
    }

    @Override
    public void draw(Canvas canvas) {
        spriteManager.draw(canvas);

        if(currentMenu != 0 && currentDesiredMenu == 0)
        {
            spriteManager.removeImage(10);
            spriteManager.displaySprite("menuback", 10);

            currentMenu = 0;
        }
        else if(currentMenu != 1 && currentDesiredMenu == 1)
        {
            spriteManager.removeImage(10);
            spriteManager.displaySprite("menubacksettings", 10);

            currentMenu= 1;
        }
    }

    @Override
    public LevelType desiredLevel() {
        return levelType;
    }


    @Override
    public Level exit() {

        spriteManager.exit();
        return new Board(context);
    }
}
