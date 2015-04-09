package com.gongyinan.mdmusicplayer.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gongyinan on 15/4/5.
 */
public class SearchResult implements Serializable {
    public int TotalCount;
    public String KeyWord;
    public int CurrentCount;
    public int CurrentPage;
    public ArrayList<Music> List;
}
