package com.daniel_carroll.daniel.birdgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daniel_carroll.daniel.birdgame.utility.Util;

public class CharacterSprite {

    private Bitmap image, original;
    private int x, y;
    private float xVelocity = 0;
    private float yVelocity = 0;
    private int maxSpeed = 13;


    public CharacterSprite(Bitmap bmp) {
        original = bmp;
        image = bmp;
        image = getResizedBitmap(image, (int)((float) Util.getrScreenHeight() * 0.1f), (int)((float)Util.getrScreenHeight() * 0.1f));
        x = 0;
        y = 0;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, Util.getRzX() + x, Util.getRzY() + y, null);
    }

    public void update()
    {
        if(Util.isPressed())
        {
            if(Util.gettX() > x && xVelocity <= maxSpeed)
                xVelocity++;
            else if(Util.gettX() < x && xVelocity >= -maxSpeed)
                xVelocity--;

            if(Util.gettY() > y && yVelocity <= maxSpeed)
                yVelocity++;
            else if(Util.gettY() < y && yVelocity >= -maxSpeed)
                yVelocity--;
        }
        else
        {
            if(xVelocity > 0.3)
                xVelocity = xVelocity - 0.3f;
            else if(xVelocity < -0.3)
                xVelocity = xVelocity + 0.3f;
            else
                xVelocity = 0;

            if(yVelocity > 0.3)
                yVelocity = yVelocity - 0.3f;
            else if(yVelocity < -0.3)
                yVelocity = yVelocity + 0.3f;
            else
                yVelocity = 0;
        }

        x += xVelocity;
        y += yVelocity;

        /*if (x < 0 && y < 0) {
            x = Util.getrScreenWidth() / 2;
            y = Util.getrScreenHeight() / 2;
        } else {
            x += xVelocity;
            y += yVelocity;

            if ((x > Util.getrScreenWidth() - image.getWidth()) || (x < 0)) {
                xVelocity = xVelocity * -1;
            }
            if ((y > Util.getrScreenHeight() - image.getHeight()) || (y < 0)) {
                yVelocity = yVelocity * -1;
            }
        }*/
    }

    public Bitmap drawTextToBitmap(String gText)
    {
        int scale = 3;

        Bitmap bitmap = image.copy(image.getConfig(), true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize((int) (14 * scale));

        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        //bm.recycle();
        return resizedBitmap;
    }

}
