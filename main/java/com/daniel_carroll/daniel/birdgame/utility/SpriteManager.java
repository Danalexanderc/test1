package com.daniel_carroll.daniel.birdgame.utility;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.daniel_carroll.daniel.birdgame.graphics.Sprite;

import java.util.ArrayList;

public class SpriteManager {

    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private ArrayList<String> spriteNames = new ArrayList<String>();

    private ArrayList<Image> images = new ArrayList<Image>();

    private long deltaTime;

    public SpriteManager(long dT)
    {
        deltaTime = dT;
    }

    public void loadSprite(Context c, int resID, String spriteName, double xResize, double yResize)
    {
        loadSprite(c,resID,spriteName,xResize,yResize,true);
    }

    public void loadSprite(Context c, int resID, String spriteName, double xResize, double yResize, boolean screenResize)
    {
        if(screenResize)
            sprites.add(new Sprite(BitmapFactory.decodeResource(c.getResources(), resID),
                (int)((float) Util.getrScreenWidth() * xResize),
                (int)((float) Util.getrScreenHeight() * yResize)));
        else
            sprites.add(new Sprite(BitmapFactory.decodeResource(c.getResources(), resID),
                    (int)(Util.screenConversionRatio*xResize),
                    (int)(Util.screenConversionRatio*yResize)));
        spriteNames.add(spriteName);
    }

    public void update(long dT)
    {
        deltaTime = dT;

        for(int i = 0; i < images.size(); i++)
        {
            if(images.get(i).movementType == MovementType.STRAIGHT && deltaTime > images.get(i).startTime)
            {
                double progress = (deltaTime - images.get(i).startTime)/images.get(i).duration;

                images.get(i).pos.x = images.get(i).startPos.x + (int)(-progress * (images.get(i).startPos.x - images.get(i).endPos.x));
                images.get(i).pos.y = images.get(i).startPos.y + (int)(-progress * (images.get(i).startPos.y - images.get(i).endPos.y));
            }
        }
    }

    public void draw(Canvas canvas)
    {
        for(int i = 0; i < images.size(); i++)
        {
            if(deltaTime > images.get(i).startTime)
            {
                if(deltaTime > images.get(i).startTime + images.get(i).duration && images.get(i).duration != -1)
                {
                    images.remove(i);
                    i--;
                }
                else if(images.get(i).visible){
                    sprites.get(spriteNames.indexOf(images.get(i).spriteName)).draw(canvas, images.get(i).pos.x, images.get(i).pos.y);
                }
            }
        }
    }

    // duration should be passed in in seconds
    public void displaySprite(String spriteName, int priority, int x, int y, double duration)
    {
        Image i = new Image();

        i.duration = duration * 1000;
        i.movementType = MovementType.STATIONARY;
        i.priority = priority;
        i.spriteName = spriteName;
        i.startTime = deltaTime;
        i.pos = new Pos(x,y);

        images.add(placePriority(priority), i);
    }

    public void displaySprite(String spriteName, int priority, double duration)
    { displaySprite(spriteName, priority, 0, 0, duration); }

    public void displaySprite(String spriteName, int priority)
    { displaySprite(spriteName, priority,0, 0, -0.001); }

    public void displayDelayedSprite(String spriteName, int priority, int x, int y, double duration, double delay)
    {
        Image i = new Image();

        i.duration = duration * 1000;
        i.movementType = MovementType.STATIONARY;
        i.priority = priority;
        i.spriteName = spriteName;
        i.startTime = (long)(delay * 1000 + deltaTime);


        i.pos = matchScreen(x,y);

        images.add(placePriority(priority), i);
    }

    public void displayDelayedSprite(String spriteName, int priority, int x, int y, double delay)
    { displayDelayedSprite(spriteName, priority, x, y, -0.001, delay); }

    public void displayDelayedSprite(String spriteName, int priority, double duration, double delay)
    { displayDelayedSprite(spriteName, priority, 0, 0, duration, delay); }

    public void displayDelayedSprite(String spriteName, int priority, double delay)
    { displayDelayedSprite(spriteName, priority, 0, 0, -0.001, delay); }

    public void displayMovingSprite(String spriteName, int priority, int startX, int startY, int endX, int endY, double duration, double delay)
    {
        Image i = new Image();

        i.duration = duration * 1000;
        i.movementType = MovementType.STRAIGHT;
        i.priority = priority;
        i.spriteName = spriteName;
        i.startTime = (long)(delay * 1000 + deltaTime);

        i.pos = matchScreen(startX,startY);
        i.startPos = matchScreen(startX,startY);
        i.endPos = matchScreen(endX,endY);

        images.add(placePriority(priority), i);
    }

    private int placePriority(int priority)
    {
        int u;

        for(u = 0; u < images.size(); u++)
        {
            if(priority <= images.get(u).priority)
                break;
        }

        return u;
    }

    public enum MovementType
    {
        STATIONARY, STRAIGHT,
    }

    public Image getImages(int priority)
    {
        return images.get(getImagesIndex(priority));
    }

    public int getImagesSize()
    {
        return images.size();
    }

    private int getImagesIndex(int priority)
    {
        for(int q = 0; q < images.size(); q++)
        {
            if(images.get(q).priority == priority)
                return q;
            else if(images.get(q).priority > priority)
                break;
        }

        return -1;
    }

    public void removeImage(int priority)
    {
        images.remove(getImagesIndex(priority));
    }

    private Pos matchScreen(int x, int y)
    {
        return new Pos((int)(Util.screenConversionRatio * x), (int)(Util.screenConversionRatio * y));
    }

    public void exit()
    {
        for(int x = 0; x < sprites.size(); x++)
        {
            sprites.get(x).free();
        }

        sprites.clear();
        spriteNames.clear();
        images.clear();
    }


}