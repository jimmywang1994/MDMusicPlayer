package com.gongyinan.mdmusicplayer.MusicPlayer;

import android.media.MediaPlayer;

import com.gongyinan.mdmusicplayer.Model.Music;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gongyinan on 15/4/5.
 */
public class Player {
    private static MediaPlayer player= new MediaPlayer();

    private static FileDescriptor toFileDescriptor(byte[] data,Music music) throws IOException {
        File file = File.createTempFile(music.ID,".m4a");
        file.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
        FileInputStream fis = new FileInputStream(file);
        return fis.getFD();
    }

    public static MediaPlayer getPlayerInstance(){
        return player;
    }

    public static MediaPlayer setMusicData(byte[] data,Music music) throws IOException {
        if (player.isPlaying()){
            player.stop();
        }
        player = new MediaPlayer();
        //TODO 不要重新实例化
        player.setDataSource(toFileDescriptor(data,music));
        player.prepare();
        return player;
    }

    public static void setMusicInfo(Music music){

    }
}