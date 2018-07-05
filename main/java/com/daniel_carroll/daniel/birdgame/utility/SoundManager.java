package com.daniel_carroll.daniel.birdgame.utility;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.SystemClock;

import com.daniel_carroll.daniel.birdgame.R;

import java.util.ArrayList;

public class SoundManager {

    private long deltaTime;
    private ArrayList<String> soundNames = new ArrayList<String>();
    private ArrayList<MediaPlayer> sounds = new ArrayList<MediaPlayer>();
    private ArrayList<MediaPlayer> activeSounds = new ArrayList<MediaPlayer>();
    private ArrayList<Long> startTimes = new ArrayList<Long>();
    private ArrayList<Boolean> started = new ArrayList<Boolean>();
    private ArrayList<Integer> volumes = new ArrayList<Integer>();

    public SoundManager(long dT)
    {
        deltaTime = dT;
    }

    public void loadSound(Context c, int resID, String soundName)
    {
        sounds.add(MediaPlayer.create(c, resID));
        soundNames.add(soundName);
    }

    public void update(long dT)
    {
        deltaTime = dT;

        for(int i = 0; i < activeSounds.size(); i++)
        {
            if(!started.get(i) && deltaTime > startTimes.get(i))
            {
                if(!Util.muted)
                    activeSounds.get(i).start();

                float log1=(float)(Math.log(100-(Util.appVolume + volumes.get(i)))/Math.log(100));
                activeSounds.get(i).setVolume(1-log1, 1-log1);
                started.set(i, true);
            }
            else if(started.get(i) && !activeSounds.get(i).isPlaying())
            {
                activeSounds.remove(i);
                startTimes.remove(i);
                started.remove(i);
                i--;
            }
        }
    }

    public void playSound(String sound) {
        playSound(sound, 0, Util.maxVolume);
    }

    public void playSound(String sound, double delay) {
        playSound(sound, delay, Util.maxVolume);
    }

    public void playSound(String sound, double delay, int volume)
    {
        activeSounds.add(sounds.get(soundNames.indexOf(sound)));
        startTimes.add((long) (delay * 1000 + deltaTime));
        volumes.add(volume);
        started.add(false);
    }

    public void stopAllSounds(boolean clearSounds)
    {
        if(clearSounds) {
            for (int i = 0; i < activeSounds.size(); i++) {
                activeSounds.get(i).stop();
                activeSounds.remove(i);
                startTimes.remove(i);
                volumes.remove(i);
                started.remove(i);
                i--;
            }
        }
        else {
            for (int i = 0; i < activeSounds.size(); i++) {
                if (activeSounds.get(i).isPlaying()) {
                    activeSounds.get(i).pause();
                }
            }
        }
    }

    public void resumeSounds()
    {
        for (int i = 0; i < activeSounds.size(); i++) {
            if (started.get(i)) {
                activeSounds.get(i).start();
            }
        }
    }

}
