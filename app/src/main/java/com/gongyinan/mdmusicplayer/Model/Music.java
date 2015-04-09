package com.gongyinan.mdmusicplayer.Model;

import java.io.Serializable;

/**
 * Created by gongyinan on 15/4/5.
 */
public class Music implements Serializable {
    public String ID ;
    public String Name ;
    public String AlbumName ;
    public String AlbumPictureID ;
    public String SongPictureID ;
    public String Picture ;
    public String Singer ;
    public String SingerPicture ;
    public int Duration ;
    public boolean SingleSong ;
    public boolean IsQQMusicResource=true;
    public String MusicUrl;
}