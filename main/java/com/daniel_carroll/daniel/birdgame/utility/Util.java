package com.daniel_carroll.daniel.birdgame.utility;



import android.content.res.Resources;
import android.graphics.Rect;

public class Util {

    public enum Bars{
        IDEAL, SIDES, TOPS
    }

    /*
     *  tX and tY are the X and Y coordinates for the user input.
     *  If the user input is out of bounds, tX or tY will register
     *  the input as being right on the edge of the valid area.
     *
     *  rzX and rzY indicate the position of the relative zero X
     *  and the relative zero Y. In other words, the "new (0,0)"
     *  when the ratio adjusted black bars are added.
     */

    private static int tX = 0;
    private static int tY = 0;
    private static int rzX = 0;
    private static int rzY = 0;

    public final static int screenWidth = 2160;
    public final static int screenHeight = 3840;
    public final static int maxVolume = 50; /* All in app volume is on a scale from 0 - 50. Higher is louder.*/
    public static int appVolume = 30; /* on a scale from 0 - 50. Zero is the quietest possible, but not muted. */
    public static boolean muted = false;

    private static int rScreenWidth = 0;
    private static int rScreenHeight = 0;

    public static double screenConversionRatio;

    private static boolean pressed = false;

    private static int actualScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static int actualScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private static float ratio = (float) actualScreenHeight /(float) actualScreenWidth, idealRatio = 16.0f/9.0f;

    private static Bars bars;
    private static Rect bar1 = new Rect(), bar2 = new Rect();

    public Util()
    {

    }

    public static void init()
    {
        actualScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        actualScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        ratio = (float) actualScreenHeight /(float) actualScreenWidth;

        if(ratio == idealRatio) {
            bars = Bars.IDEAL;

            rzX = 0;
            rzY = 0;
            rScreenWidth = actualScreenWidth;
            rScreenHeight = actualScreenHeight;
        }
        else if(ratio < idealRatio) {
            bars = Bars.SIDES;

            float boxWidth = ((float) actualScreenWidth - ((float) actualScreenHeight / idealRatio))/2f;

            bar1.set(0,0, (int)boxWidth, actualScreenHeight);
            bar2.set(actualScreenWidth - (int)boxWidth,0, actualScreenWidth, actualScreenHeight);

            rzX = (int)boxWidth;
            rzY = 0;
            rScreenWidth = actualScreenWidth - (int)boxWidth*2;
            rScreenHeight = actualScreenHeight;
        }
        else {
            bars = Bars.TOPS;

            float boxHeight = ((float) actualScreenHeight - (idealRatio * (float) actualScreenWidth)) / 2f;

            bar1.set(0,0, actualScreenWidth, (int)boxHeight);
            bar2.set(0, actualScreenHeight - (int)boxHeight, actualScreenWidth, actualScreenHeight);

            rzX = 0;
            rzY = (int)boxHeight;
            rScreenWidth = actualScreenWidth;
            rScreenHeight = actualScreenHeight - (int)boxHeight*2;
        }


        screenConversionRatio = (double)rScreenHeight/(double)screenHeight;
    }


    public static void update()
    {

    }

    public static int gettX() {
        return tX;
    }

    public static int gettY() {
        return tY;
    }

    public static void settX(int tX) {
        Util.tX = tX;

        if(bars == Bars.SIDES)
        {
            if(Util.tX < rzX)
                Util.tX = 0;
            else if(Util.tX > rzX + rScreenWidth)
                Util.tX = rScreenWidth;
            else
                Util.tX = Util.tX - rzX;
        }

        Util.tX = (int)(Util.tX / screenConversionRatio);
    }

    public static void settY(int tY) {

        Util.tY = tY;

        if(bars == Bars.TOPS)
        {
            if(Util.tY < rzY)
                Util.tY = 0;
            else if(Util.tY > rzY + rScreenHeight)
                Util.tY = rScreenHeight;
            else
                Util.tY = Util.tY - rzY;
        }

        Util.tY = (int)(Util.tY / screenConversionRatio);
    }

    public static int getRzX() {
        return rzX;
    }

    public static int getRzY() {
        return rzY;
    }

    public static void setPressed(boolean pressed) {
        Util.pressed = pressed;
    }

    public static boolean isPressed() {
        return pressed;
    }

    public static Bars getBars() {
        return bars;
    }

    public static Rect getBar1(){
        return bar1;
    }

    public static Rect getBar2(){
        return bar2;
    }

    public static int getrScreenWidth() {
        return rScreenWidth;
    }

    public static int getrScreenHeight() {
        return rScreenHeight;
    }

}
