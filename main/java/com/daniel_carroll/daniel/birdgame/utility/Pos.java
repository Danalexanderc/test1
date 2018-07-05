package com.daniel_carroll.daniel.birdgame.utility;

public class Pos {
    public int x, y;

    public Pos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Pos(Pos p)
    {
        this.x = p.x;
        this.y = p.y;
    }
}
