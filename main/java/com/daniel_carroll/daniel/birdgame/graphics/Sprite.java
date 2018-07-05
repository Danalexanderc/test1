package com.daniel_carroll.daniel.birdgame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.daniel_carroll.daniel.birdgame.utility.Util;

public class Sprite {

    private Bitmap image;

    public Sprite(Bitmap bmp) {
        image = bmp;
    }

    // xResize and yResize determine what fraction of the screen size the image is.
    public Sprite(Bitmap bmp, int xResize, int yResize) {
        image = getResizedBitmap(bmp, xResize, yResize);
        bmp.recycle();
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, Util.getRzX(), Util.getRzY(), null);
    }

    public void draw(Canvas canvas, int x, int y)
    {
        canvas.drawBitmap(image, Util.getRzX() + x, Util.getRzY() + y, null);
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

        //bm.recycle();
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    public void free()
    {
        image.recycle();
    }
}
