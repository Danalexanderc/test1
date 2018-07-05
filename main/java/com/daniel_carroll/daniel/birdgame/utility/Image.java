package com.daniel_carroll.daniel.birdgame.utility;

public class Image {
    public String spriteName;
    public int priority; /* An Image with a higher priority will be placed in front of a lower priority by SpriteManager. */
    public long startTime;
    public double duration; /* If an Image has a duration of -1, SpriteManager will display that image indefinitely. */
    public Pos pos, startPos, endPos;
    public SpriteManager.MovementType movementType;
    public boolean visible = true;

}
