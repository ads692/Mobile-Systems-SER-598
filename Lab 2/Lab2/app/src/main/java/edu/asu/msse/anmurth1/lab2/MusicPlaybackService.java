package edu.asu.msse.anmurth1.lab2;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.MediaController;

/**
 * Copyright 2015 Aditya Narasimhamurthy.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author: Aditya Narasimhamurthy  mailto:aditya.murthy@asu.edu.
 * @version: January 24, 2015
 */
public class MusicPlaybackService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer player;
    private boolean isPlaying = false;
    public static final String COMMAND = "CMD";
    public static final int START = 0;
    public static final int STOP = 1;
    public static final int PAUSE = 2;
    public static final int RESUME = 3;
    public static final int NONE = 4;

    public void onCreate(){
        super.onCreate();
        android.util.Log.d(getClass().getSimpleName(), "in onCreate()");
        player = MediaPlayer.create(getApplicationContext(), R.raw.batman);
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnCompletionListener(this);
        isPlaying = false;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int command = intent.getIntExtra(COMMAND,NONE);
        android.util.Log.d(getClass().getSimpleName(), "in onStartCommand()" +command);
        if (command == PAUSE){
            if(player!= null && isPlaying){
                player.pause();
                isPlaying = false;
            }
        } else if (command == RESUME){
            player.start();
            isPlaying = true;
        } else if (command == START){
            if(isPlaying){
                player.pause();
                player.seekTo(0);
            }
            player.start();
            isPlaying = true;
        }else if (command == STOP){
            isPlaying = false;
            stopSelf();
        }

        return (START_NOT_STICKY);
    }

    @Override
    public void onDestroy() {
        if( player!= null){
            player.stop();
            player.release();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent){ return(null);}

    @Override
    public boolean onUnbind(Intent intent) { return  false; }

    public void onCompletion(MediaPlayer mp){
        mp.seekTo(0);
        mp.start();
        isPlaying = true;
    }


}