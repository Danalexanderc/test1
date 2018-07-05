package com.daniel_carroll.daniel.birdgame.screens;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.daniel_carroll.daniel.birdgame.CharacterSprite;
import com.daniel_carroll.daniel.birdgame.R;

public class Board extends Level {

    private static CharacterSprite characterSprite = null;

    public Board(Context c) {
        context = c;

        levelType = LevelType.BOARD;

        if(characterSprite == null)
            characterSprite = new CharacterSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.jeff));

    }

    @Override
    public void update() {

        characterSprite.update();

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(200,100,100));

        characterSprite.draw(canvas);
    }

    @Override
    public LevelType desiredLevel() {
        return levelType;
    }


    @Override
    public Level exit() {
        return null;
    }
}
